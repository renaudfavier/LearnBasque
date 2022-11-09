package com.renaudfavier.learnbasque.feature.vocabulary.domain

import android.util.Log
import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.GetUserLevelUseCase
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.network.Dispatcher
import com.renaudfavier.learnbasque.core.network.LearnBasqueDispatchers.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNextWordToMemorizeUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val userAnswerRepository: UserAnswerRepository,
    private val getUserLevelUseCase: GetUserLevelUseCase,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(): Word = withContext(defaultDispatcher) {
        val userLevel = getUserLevelUseCase().first()
        Log.i("oji", "userLevel $userLevel")
        return@withContext wordsRepository.getWords().random()
//        val words = wordsRepository.getWords()
//
//        val wordsOfUserLevel = words.filter { it.complexity <= userLevel + 5 }
//        val idsOfUserLevel = wordsOfUserLevel.map { it.id }
//
//        val wordId = getNextWordId(idsOfUserLevel)
//        return@withContext words.find { it.id == wordId } ?: wordsOfUserLevel.first()
    }

    private suspend fun getNextWordId(idsOfUserLevel: List<String>) =
        userAnswerRepository
            .getAnswers()
            .filter { idsOfUserLevel.contains(it.questionId) }
            .groupBy { it.questionId }
            .map { it.key to computeMastering(it.value) }
            .sortedWith(BestMasteringComparator())
            .firstOrNull()
            ?.first

    //0 to 1, 0 is not mastered at all, 1 is perfectly mastered
    private fun computeMastering(wordsEntry: List<UserAnswer>): Float? {
        return 0f
        //val lastTry = wordsEntry.minByOrNull { it.date } ?: return null
        //return if (lastTry.isCorrect) 1f else 0f
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
