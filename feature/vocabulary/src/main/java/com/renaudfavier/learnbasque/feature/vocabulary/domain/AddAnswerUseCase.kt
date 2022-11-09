package com.renaudfavier.learnbasque.feature.vocabulary.domain

import android.util.Log
import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.network.Dispatcher
import com.renaudfavier.learnbasque.core.network.LearnBasqueDispatchers.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class AddAnswerUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val userAnswerRepository: UserAnswerRepository,
    @Dispatcher(Default)  private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        wordId: String,
        answer: String,
    ): Boolean = withContext(defaultDispatcher) {
        val word = wordsRepository.getWord(wordId)!!
        val isCorrect = answer == word.french
        val memoryTestAnswer = UserAnswer(
            questionId = word.id,
            answer = QuestionAnswer.AnswerString(answer),
            date = Clock.System.now()
        )
        userAnswerRepository.addAnswer(memoryTestAnswer)
        Log.i("test", "count: "+ userAnswerRepository.getAnswers().count())
        return@withContext isCorrect
    }
}
