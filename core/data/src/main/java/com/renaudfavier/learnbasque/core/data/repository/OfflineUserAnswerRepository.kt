package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.data.model.toEntity
import com.renaudfavier.learnbasque.core.database.dao.UserAnswerDao
import com.renaudfavier.learnbasque.core.database.entity.UserAnswerEntity
import com.renaudfavier.learnbasque.core.database.entity.asExternalModel
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.network.Dispatcher
import com.renaudfavier.learnbasque.core.network.LearnBasqueDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineUserAnswerRepository @Inject constructor(
    private val dao: UserAnswerDao,
    @Dispatcher(LearnBasqueDispatchers.IO)  private val ioDispatcher: CoroutineDispatcher
): UserAnswerRepository {

    override suspend fun getAnswersStream(): Flow<List<UserAnswer>> = withContext(ioDispatcher) {
        dao.getUserAnswersStream().map { it.map(UserAnswerEntity::asExternalModel) }
    }

    override suspend fun getAnswers(): List<UserAnswer> = withContext(ioDispatcher) {
        dao.getUserAnswers().map(UserAnswerEntity::asExternalModel)
    }

    override suspend fun addAnswer(answer: UserAnswer) = withContext(ioDispatcher) {
        dao.insertAnswer(answer.toEntity())
    }
}
