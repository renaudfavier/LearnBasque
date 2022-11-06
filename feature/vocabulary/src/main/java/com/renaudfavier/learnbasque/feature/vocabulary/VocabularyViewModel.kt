package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.result.asResult
import com.renaudfavier.learnbasque.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.onStart

class VocabularyViewModel(
    wordsRepository: WordsRepository
) : ViewModel() {

    val vocabularyUiState: StateFlow<VocabularyUiState> = vocabularyUiStateStream(
        wordsRepository = wordsRepository
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VocabularyUiState.Loading
    )

    fun answerProposition1() {}
    fun answerProposition2() {}
}

private fun vocabularyUiStateStream(
    wordsRepository: WordsRepository
) = wordsRepository
    .getWordsStream()
    .asResult()
    .map { wordsResult ->
        when (wordsResult) {
            is Result.Success -> {
                val word = wordsResult.data.first()
                val word2 = wordsResult.data.last()
                VocabularyUiState.EasyMemoryCardUiModel(
                    id = "",
                    wordToTranslate = word.basqueTranslation,
                    proposition1 = word2.frenchTranslation,
                    proposition2 = word.frenchTranslation
                )
            }
            is Result.Loading -> VocabularyUiState.Loading
            is Result.Error -> VocabularyUiState.Error
        }

    }

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
