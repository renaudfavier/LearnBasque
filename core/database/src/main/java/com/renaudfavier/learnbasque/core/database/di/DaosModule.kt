package com.renaudfavier.learnbasque.core.database.di

import com.renaudfavier.learnbasque.core.database.LearnBasqueDatabase
import com.renaudfavier.learnbasque.core.database.dao.UserAnswerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesUserAnswerDao(
        database: LearnBasqueDatabase,
    ): UserAnswerDao = database.userAnswerDao()

}
