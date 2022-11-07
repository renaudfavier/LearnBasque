package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.MemoryTestAnswer
import kotlinx.coroutines.flow.Flow

interface MemoryTestAnswerRepository {

    fun getAnswersStream(): Flow<List<MemoryTestAnswer>>

    suspend fun getAnswers(): List<MemoryTestAnswer>

    suspend fun addAnswer(answer: MemoryTestAnswer)
}
