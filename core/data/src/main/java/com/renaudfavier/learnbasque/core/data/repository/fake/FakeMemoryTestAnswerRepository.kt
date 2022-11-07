package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.MemoryTestAnswerRepository
import com.renaudfavier.learnbasque.core.model.data.MemoryTestAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMemoryTestAnswerRepository: MemoryTestAnswerRepository {

    private var answers: MutableList<MemoryTestAnswer> = mutableListOf()

    private val flow = MutableStateFlow<List<MemoryTestAnswer>>(emptyList())

    override fun getAnswersStream(): Flow<List<MemoryTestAnswer>> {
        return flow
    }

    override suspend fun getAnswers(): List<MemoryTestAnswer> {
        return answers
    }

    override suspend fun addAnswer(answer: MemoryTestAnswer) {
        answers.add(answer)
        flow.emit(answers)
    }
}
