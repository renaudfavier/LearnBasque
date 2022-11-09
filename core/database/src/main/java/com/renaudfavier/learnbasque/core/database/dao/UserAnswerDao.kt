package com.renaudfavier.learnbasque.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renaudfavier.learnbasque.core.database.entity.USER_ANSWER_TABLE_NAME
import com.renaudfavier.learnbasque.core.database.entity.UserAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAnswerDao {

    @Query(value = "SELECT * FROM $USER_ANSWER_TABLE_NAME")
    fun getUserAnswersStream(): Flow<List<UserAnswerEntity>>

    @Query(value = "SELECT * FROM $USER_ANSWER_TABLE_NAME")
    fun getUserAnswers(): List<UserAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnswer(answer: UserAnswerEntity)
}
