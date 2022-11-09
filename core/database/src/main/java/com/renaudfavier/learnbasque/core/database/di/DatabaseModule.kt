package com.renaudfavier.learnbasque.core.database.di

import android.content.Context
import androidx.room.Room
import com.renaudfavier.learnbasque.core.database.LearnBasqueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesLearnBasqueDatabase(
        @ApplicationContext context: Context,
    ): LearnBasqueDatabase = Room.databaseBuilder(
        context,
        LearnBasqueDatabase::class.java,
        "learnbasque-database"
    ).build()
}
