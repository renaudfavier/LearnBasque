package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserAnswerRepository: UserAnswerRepository {

    private var answers: MutableList<UserAnswer> = mutableListOf()

    private val flow = MutableStateFlow<List<UserAnswer>>(emptyList())

    override fun getAnswersStream(): Flow<List<UserAnswer>> {
        return flow
    }

    override suspend fun getAnswers(): List<UserAnswer> {
        return answers
    }

    override suspend fun addAnswer(answer: UserAnswer) {
        answers.add(answer)
        flow.emit(answers)
    }
}
