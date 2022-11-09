package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.data.model.toEntity
import com.renaudfavier.learnbasque.core.database.dao.UserAnswerDao
import com.renaudfavier.learnbasque.core.database.entity.UserAnswerEntity
import com.renaudfavier.learnbasque.core.database.entity.asExternalModel
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineUserAnswerRepository @Inject constructor(
    private val dao: UserAnswerDao
): UserAnswerRepository {

    override fun getAnswersStream(): Flow<List<UserAnswer>> = dao
        .getUserAnswersStream()
        .map { it.map(UserAnswerEntity::asExternalModel) }

    override suspend fun getAnswers(): List<UserAnswer> = dao
        .getUserAnswers()
        .map(UserAnswerEntity::asExternalModel)

    override suspend fun addAnswer(answer: UserAnswer) = dao
        .insertAnswer(answer.toEntity())
}
