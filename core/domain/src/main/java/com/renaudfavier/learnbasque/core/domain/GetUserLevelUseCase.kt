package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.MemoryTestAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserLevelUseCase @Inject constructor(
    private val memoryTestAnswerRepository: MemoryTestAnswerRepository,
) {

    suspend operator fun invoke(): Flow<Int> {
        return memoryTestAnswerRepository.getAnswersStream().map { it.size }
    }
}
