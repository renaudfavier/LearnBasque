package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import kotlinx.coroutines.flow.Flow

interface UserAnswerRepository {

    suspend fun getAnswersStream(): Flow<List<UserAnswer>>

    suspend fun getAnswers(): List<UserAnswer>

    suspend fun addAnswer(answer: UserAnswer)
}
