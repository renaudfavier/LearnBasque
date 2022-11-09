package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.learnbasque.core.data.repository.BaseWordsRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.IsAnswerCorrectUseCase
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.feature.vocabulary.domain.AddAnswerUseCase
import com.renaudfavier.learnbasque.feature.vocabulary.domain.GetNextWordToMemorizeUseCase
import com.renaudfavier.learnbasque.core.result.asResult
import com.renaudfavier.learnbasque.core.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class VocabularyViewModel @Inject constructor(
    private val getNextWordToMemorize: GetNextWordToMemorizeUseCase,
    private val wordsRepository: WordsRepository,
    private val addAnswerUseCase: AddAnswerUseCase,
    private val isAnswerCorrectUseCase: IsAnswerCorrectUseCase,
) : ViewModel() {

    val vocabularyUiState = MutableStateFlow<VocabularyUiState>(VocabularyUiState.Loading)

    fun start() = viewModelScope.launch {
        vocabularyUiState.emit(getNextWordToMemorize().mapToNewQuestionUiModel())
    }

    fun answerProposition1() = viewModelScope.launch {
        val model = vocabularyUiState.value as? VocabularyUiState.EasyMemoryCardUiModel ?: return@launch
        answer(model.proposition1, model)
    }

    fun answerProposition2() = viewModelScope.launch {
        val model = vocabularyUiState.value as? VocabularyUiState.EasyMemoryCardUiModel ?: return@launch
        answer(model.proposition2, model)
    }

    private suspend fun answer(proposition: String, model: VocabularyUiState.EasyMemoryCardUiModel) {
        val isCorrect = isAnswerCorrectUseCase(
            Exercise.TranslateFromBasque(model.wordId),
            QuestionAnswer.AnswerString(proposition)
        )
        addAnswerUseCase.invoke(model.wordId, proposition)
        showResult(isCorrect, model)
        vocabularyUiState.emit(getNextWordToMemorize().mapToNewQuestionUiModel())
    }

    private suspend fun showResult(
        isCorrect: Boolean,
        model: VocabularyUiState.EasyMemoryCardUiModel
    ) {
        val color: Color = if (isCorrect) Color.Green else Color.Red
        vocabularyUiState.emit(model.copy(backgroundColor = color))
        delay(1000L)
    }

    private suspend fun Word.mapToNewQuestionUiModel() : VocabularyUiState.EasyMemoryCardUiModel {

        val wrongOne = wordsRepository.getWords().filterNot {it.id == id}.random().french
        val isRightIsFirstPosition = Random.nextBoolean()

        return VocabularyUiState.EasyMemoryCardUiModel(
            wordId = id,
            wordToTranslate = basque,
            proposition1 = if(isRightIsFirstPosition) french else wrongOne,
            proposition2 = if(isRightIsFirstPosition) wrongOne else french
        )
    }
}

sealed interface VocabularyUiState {
    data class EasyMemoryCardUiModel(
        val wordId: String,
        val wordToTranslate: String,
        val proposition1: String,
        val proposition2: String,
        val backgroundColor: Color? = null,
    ) : VocabularyUiState
    object Loading: VocabularyUiState
    object Error: VocabularyUiState
}
