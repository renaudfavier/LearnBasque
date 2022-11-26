package com.renaudfavier.learnbasque.feature.microlearning

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.GetNextLearningUnitUseCase
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Lesson
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.ui.OrigamiBackgroundAnimator
import com.renaudfavier.learnbasque.core.ui.ProgressButtonConfig
import com.renaudfavier.learnbasque.core.ui.VocabularyQuestionCardConfiguration
import com.renaudfavier.learnbasque.core.domain.AnswerVocabularyExerciseUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MicroLearningViewModel @Inject constructor(
    private val getNextLearningUnitUseCase: GetNextLearningUnitUseCase,
    private val wordsRepository: WordsRepository,
    private val answerVocabularyExerciseUseCase: AnswerVocabularyExerciseUseCase,
) : ViewModel() {

    val microLearningUiState = MutableStateFlow<MicroLearningUiState>(MicroLearningUiState.Loading)

    private val origamiBackgroundAnimator = OrigamiBackgroundAnimator(viewModelScope)
    val backgroundUiState =  origamiBackgroundAnimator.lineFLow

    private var shownExercise: GetNextLearningUnitUseCase.LearningUnit? = null

    fun start() = viewModelScope.launch {
        origamiBackgroundAnimator.start()
        showNextExercise()
    }

    private var delayJob: Job? = null

    private fun newWordSeen() {
        viewModelScope.launch {
            val newWordExercise = shownExercise as? Lesson.NewWord ?: return@launch
            //newWordWasShownUseCase(newWordExercise)
            next()
        }
    }

    fun answer(answer: QuestionAnswer.AnswerString) = viewModelScope.launch {
        val exercise = (shownExercise as? GetNextLearningUnitUseCase.LearningUnit.Exo)?.exercise ?: return@launch
        when(val result = answerVocabularyExerciseUseCase(exercise, answer)) {
            is AnswerVocabularyExerciseUseCase.Response.Error -> TODO()
            is AnswerVocabularyExerciseUseCase.Response.Success -> showCorrection(result.isCorrect, answer, result.correction)
        }
        delayJob = showNextExerciseAfterDelay()
    }

    fun next() {
        viewModelScope.launch {
            delayJob?.cancel()
            showNextExercise()
        }
    }

    private suspend fun showCorrection(
        isCorrect: Boolean,
        userAnswer: QuestionAnswer,
        correction: QuestionAnswer
    ) {

    }

    private suspend fun showNextExerciseAfterDelay(): Job = viewModelScope.launch {
        val initialState = microLearningUiState.value as? MicroLearningUiState.Content.Translation ?: return@launch
        val progressButtonConfig = ProgressButtonConfig(
            onClick = { next() },
            progress = 0f,
            alpha = 0.7f
        )
        microLearningUiState.value = initialState.copy(buttonConfig = progressButtonConfig)

        for(i in 1..33) {
            val state = microLearningUiState.value as? MicroLearningUiState.Content.Translation ?: return@launch
            microLearningUiState.value = state.copy(buttonConfig = progressButtonConfig.copy(progress = i*3f/100f))
            delay(30)
        }
        showNextExercise()
    }

    private suspend fun showNextExercise() = viewModelScope.launch {
        val nextExercise = getNextLearningUnitUseCase()
        val state = nextExercise.mapToUiState()
        microLearningUiState.emit(state)
        shownExercise = nextExercise
    }

    private suspend fun GetNextLearningUnitUseCase.LearningUnit.mapToUiState(): MicroLearningUiState = when(val unit = this) {
        is GetNextLearningUnitUseCase.LearningUnit.Exo -> unit.exercise.mapToUiState()
        is GetNextLearningUnitUseCase.LearningUnit.Less -> unit.lesson.mapToUiState()
    }

    private suspend fun Lesson.mapToUiState() = when(val lesson = this) {
        is Lesson.NewWord -> wordsRepository.getWord(lesson.wordId)
            ?.let {
                MicroLearningUiState.Content.NewWord(
                    basque = it.basque,
                    french = it.french,
                    onOkClick = { newWordSeen() }
                )
            } ?: MicroLearningUiState.Error
    }

    private suspend fun Exercise.mapToUiState() = when(val exercise = this) {

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
        data class NewWord(val basque: String, val french: String, val onOkClick: () -> Unit): Content
        data class Translation(
            val cardConfig: VocabularyQuestionCardConfiguration,
            val buttonConfig: ProgressButtonConfig? = null,
        ): Content
    }
    object Loading: MicroLearningUiState
    object Error: MicroLearningUiState
}
