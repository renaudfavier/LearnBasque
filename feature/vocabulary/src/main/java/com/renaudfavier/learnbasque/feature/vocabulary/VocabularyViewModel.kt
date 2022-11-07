package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.feature.vocabulary.domain.AddAnswerUseCase
import com.renaudfavier.learnbasque.feature.vocabulary.domain.GetNextWordToMemorizeUseCase
import com.renaudfavier.learnbasque.core.result.asResult
import com.renaudfavier.learnbasque.core.result.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VocabularyViewModel(
    private val getNextWordToMemorize: GetNextWordToMemorizeUseCase,
    private val addAnswerUseCase: AddAnswerUseCase,
) : ViewModel() {

    val vocabularyUiState = MutableStateFlow<VocabularyUiState>(VocabularyUiState.Loading)

    fun start() = viewModelScope.launch {
        vocabularyUiState.emit(getNextWordToMemorize().mapToNewQuestionUiModel())
    }
    //= vocabularyUiStateStream(
     //   nextWord = getNextWordToMemorize
    //).stateIn(
     //   scope = viewModelScope,
    //    started = SharingStarted.WhileSubscribed(5_000),
     //   initialValue = VocabularyUiState.Loading
    //)



    fun answerProposition1() = viewModelScope.launch {
        val model = vocabularyUiState.value as? VocabularyUiState.EasyMemoryCardUiModel ?: return@launch
        vocabularyUiState.emit(model.copy(backgroundColor = Color.Cyan))
        val isCorrect = addAnswerUseCase.invoke(model.id, model.proposition1)
        showResultAndDisplayNext(isCorrect, model)
    }

    fun answerProposition2() = viewModelScope.launch {
        val model = vocabularyUiState.value as? VocabularyUiState.EasyMemoryCardUiModel ?: return@launch
        vocabularyUiState.emit(model.copy(backgroundColor = Color.Cyan))
        val isCorrect = addAnswerUseCase.invoke(model.id, model.proposition2)
        showResultAndDisplayNext(isCorrect, model)
    }

    private suspend fun showResultAndDisplayNext(
        isCorrect: Boolean,
        model: VocabularyUiState.EasyMemoryCardUiModel
    ) {
        val color: Color = if (isCorrect) Color.Green else Color.Red
        vocabularyUiState.emit(model.copy(backgroundColor = color))
        delay(1000L)
        vocabularyUiState.emit(getNextWordToMemorize().mapToNewQuestionUiModel())
    }
}

fun Word.mapToNewQuestionUiModel() = VocabularyUiState.EasyMemoryCardUiModel(
    id = id,
    wordToTranslate = basque,
    proposition1 = "ijfoezoijf",
    proposition2 = french
)

sealed interface VocabularyUiState {
    data class EasyMemoryCardUiModel(
        val id: String,
        val wordToTranslate: String,
        val proposition1: String,
        val proposition2: String,
        val backgroundColor: Color? = null,
    ) : VocabularyUiState
    object Loading: VocabularyUiState
    object Error: VocabularyUiState
}
