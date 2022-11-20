package com.renaudfavier.learnbasque.feature.microlearning

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.GetBestExerciseToTryNextUseCase
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.ui.OrigamiBackgroundAnimator
import com.renaudfavier.learnbasque.core.ui.VocabularyQuestionCardConfiguration
import com.renaudfavier.learnbasque.feature.microlearning.domain.AddAnswerUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MicroLearningViewModel @Inject constructor(
    private val getBestExerciseToTryNextUseCase: GetBestExerciseToTryNextUseCase,
    private val wordsRepository: WordsRepository,
    private val addAnswerUseCase: AddAnswerUseCase,
) : ViewModel() {

    val microLearningUiState = MutableStateFlow<MicroLearningUiState>(MicroLearningUiState.Loading)

    private val origamiBackgroundAnimator = OrigamiBackgroundAnimator(viewModelScope)
    val backgroundUiState =  origamiBackgroundAnimator.lineFLow

    private var shownExercise: Exercise? = null

    fun start() = viewModelScope.launch {
        origamiBackgroundAnimator.start()
        showNextExercise()
    }

    fun answer(answer: QuestionAnswer) = viewModelScope.launch {
        val exercise = shownExercise ?: return@launch
        when(val result = addAnswerUseCase(exercise, answer)) {
            is AddAnswerUseCase.Response.Error -> TODO()
            is AddAnswerUseCase.Response.Success -> showCorrection(result.isCorrect, answer, result.correction)
        }
        showNextExerciseAfterDelay()
    }

    private suspend fun showCorrection(
        isCorrect: Boolean,
        userAnswer: QuestionAnswer,
        correction: QuestionAnswer
    ) {

    }

    private fun showNextExerciseAfterDelay() = viewModelScope.launch {
        delay(1000)
        showNextExercise()
    }

    private suspend fun showNextExercise() = viewModelScope.launch {
        val nextExercise = getBestExerciseToTryNextUseCase()
        val state = nextExercise.mapToUiState()
        microLearningUiState.emit(state)
        shownExercise = nextExercise
    }

    private suspend fun Exercise.mapToUiState() = when(val exercise = this) {
        is Exercise.NewWord -> wordsRepository.getWord(exercise.wordId)
            ?.let {
                MicroLearningUiState.Content.NewWord(it.basque, it.french)
            } ?: MicroLearningUiState.Error
        is Exercise.TranslateFromBasque -> wordsRepository.getWord(exercise.wordId)?.let {
            MicroLearningUiState.Content.Translation(
                cardConfig = VocabularyQuestionCardConfiguration(
                    wordToTranslate = it.basque,
                    propositions = exercise.difficulty.mapToPropositions(it.french)
                )

            )
        } ?: MicroLearningUiState.Error
        is Exercise.TranslateToBasque -> wordsRepository.getWord(exercise.wordId)?.let {
            MicroLearningUiState.Content.Translation(
                cardConfig = VocabularyQuestionCardConfiguration(
                    wordToTranslate = it.french,
                    propositions = exercise.difficulty.mapToPropositions(it.basque)
                )
            )
        } ?: MicroLearningUiState.Error
    }

    private suspend fun Exercise.TranslateToBasque.Difficulty.mapToPropositions(
        correct: String
    ): List<VocabularyQuestionCardConfiguration.Proposition> {
        val basqueWords = wordsRepository.getWords()
            .filterNot { it.basque == correct }
            .shuffled()
            .map { it.basque }
        return when(this) {
            Exercise.TranslateToBasque.Difficulty.TwoPropositions -> basqueWords.subList(0, 2)
        }.map {
            VocabularyQuestionCardConfiguration.Proposition(it, Color.LightGray)
        }
    }

    private suspend fun Exercise.TranslateFromBasque.Difficulty.mapToPropositions(
        correct: String
    ): List<VocabularyQuestionCardConfiguration.Proposition> {
        val frenchWords = wordsRepository.getWords()
            .filterNot { it.french == correct }
            .shuffled()
            .map { it.french }
        return when(this) {
            Exercise.TranslateFromBasque.Difficulty.TwoPropositions -> frenchWords.subList(0, 2)
        }.map {
            VocabularyQuestionCardConfiguration.Proposition(it, Color.LightGray)
        }
    }
}

sealed interface MicroLearningUiState {
    sealed interface Content : MicroLearningUiState {
        data class NewWord(val basque: String, val french: String): Content
        data class Translation(
            val cardConfig: VocabularyQuestionCardConfiguration
        ): Content
    }
    object Loading: MicroLearningUiState
    object Error: MicroLearningUiState
}
