package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserLevelUseCase @Inject constructor(
    private val userAnswerRepository: UserAnswerRepository,
) {

    suspend operator fun invoke(): Flow<Int> {
        return userAnswerRepository.getAnswersStream().map { it.size }
    }
}
