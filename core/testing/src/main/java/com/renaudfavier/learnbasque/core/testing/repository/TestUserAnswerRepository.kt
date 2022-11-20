package com.renaudfavier.learnbasque.core.testing.repository

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.coroutines.flow.Flow

class TestUserAnswerRepository: UserAnswerRepository {

    private var answers: List<UserAnswer> = emptyList()

    override suspend fun getAnswersStream(): Flow<List<UserAnswer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAnswers(): List<UserAnswer> = answers

    override suspend fun addAnswer(answer: UserAnswer) {
        TODO("Not yet implemented")
    }

    /**
     * A test-only API to allow setting/unsetting of answers.
     *
     */
    fun setupNextAnswers(answers: List<UserAnswer>) {
        this.answers = answers
    }
}
