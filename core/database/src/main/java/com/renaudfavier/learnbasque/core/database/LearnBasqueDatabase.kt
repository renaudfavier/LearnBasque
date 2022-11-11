package com.renaudfavier.learnbasque.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.renaudfavier.learnbasque.core.database.dao.UserAnswerDao
import com.renaudfavier.learnbasque.core.database.entity.UserAnswerEntity
import com.renaudfavier.learnbasque.core.database.util.InstantConverter

@Database(
    entities = [
        UserAnswerEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class LearnBasqueDatabase : RoomDatabase() {
    abstract fun userAnswerDao(): UserAnswerDao
}
