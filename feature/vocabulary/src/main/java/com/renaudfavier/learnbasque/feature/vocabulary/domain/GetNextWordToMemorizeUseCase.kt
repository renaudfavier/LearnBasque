package com.renaudfavier.learnbasque.feature.vocabulary.domain

import com.renaudfavier.learnbasque.core.data.repository.MemoryTestAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.GetUserLevelUseCase
import com.renaudfavier.learnbasque.core.model.data.MemoryTestAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetNextWordToMemorizeUseCase(
    private val wordsRepository: WordsRepository,
    private val memoryTestAnswerRepository: MemoryTestAnswerRepository,
    private val getUserLevelUseCase: GetUserLevelUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    suspend operator fun invoke(): Word = withContext(defaultDispatcher) {
        val userLevel = getUserLevelUseCase().first()
        val words = wordsRepository.getWords()

        val wordsOfUserLevel = words.filter { it.complexity <= userLevel + 5 }
        val idsOfUserLevel = wordsOfUserLevel.map { it.id }

        val wordId = getNextWordId(idsOfUserLevel)
        return@withContext words.find { it.id == wordId } ?: wordsOfUserLevel.first()
    }

    private suspend fun getNextWordId(idsOfUserLevel: List<String>) =
        memoryTestAnswerRepository
            .getAnswers()
            .filter { idsOfUserLevel.contains(it.wordId) }
            .groupBy { it.wordId }
            .map { it.key to computeMastering(it.value) }
            .sortedWith(BestMasteringComparator())
            .firstOrNull()
            ?.first

    //0 to 1, 0 is not mastered at all, 1 is perfectly mastered
    private fun computeMastering(wordsEntry: List<MemoryTestAnswer>): Float? {
        val lastTry = wordsEntry.minByOrNull { it.date } ?: return null
        return if (lastTry.isCorrect) 1f else 0f
    }

    class BestMasteringComparator: Comparator<Pair<String, Float?>> {
        override fun compare(p0: Pair<String, Float?>, p1: Pair<String, Float?>): Int {
            return when {
                p0.second == null -> 0
                p1.second == null -> 1
                p0.second!! > p1.second!! -> 0
                p1.second!! > p0.second!! -> 1
                else -> 0
            }
        }
    }

}
