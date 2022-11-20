/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.renaudfavier.learnbasque.core.data.di

import com.renaudfavier.learnbasque.core.data.repository.BaseWordsRepository
import com.renaudfavier.learnbasque.core.data.repository.ExerciseRepository
import com.renaudfavier.learnbasque.core.data.repository.IdBasedExerciseRepository
import com.renaudfavier.learnbasque.core.data.repository.OfflineUserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.data.repository.fake.FakeUserAnswerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideWordRepository(): WordsRepository = BaseWordsRepository()

    @Provides
    fun provideExerciseRepository(): ExerciseRepository = IdBasedExerciseRepository()

}

@Module
@InstallIn(SingletonComponent::class)
interface DataModule2 {

    @Binds
    fun bindsUserAnswerRepository(
        topicsRepository: OfflineUserAnswerRepository
    ): UserAnswerRepository
}
