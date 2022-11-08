package com.renaudfavier.learnbasque.feature.vocabulary.domain

import com.renaudfavier.learnbasque.core.data.repository.MemoryTestAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.MemoryTestAnswer
import com.renaudfavier.learnbasque.core.network.Dispatcher
import com.renaudfavier.learnbasque.core.network.LearnBasqueDispatchers.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class AddAnswerUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val memoryTestAnswerRepository: MemoryTestAnswerRepository,
    @Dispatcher(Default)  private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        wordId: String,
        answer: String,
    ): Boolean = withContext(defaultDispatcher) {
        val word = wordsRepository.getWord(wordId)!!
        val isCorrect = answer == word.french
        val memoryTestAnswer = MemoryTestAnswer(
            wordId = word.id,
            isCorrect = isCorrect,
            date = Clock.System.now()
        )
        memoryTestAnswerRepository.addAnswer(memoryTestAnswer)
        return@withContext isCorrect
    }
}
