package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.ExerciseId

interface ExerciseRepository {

    suspend fun getExercise(id: ExerciseId): Exercise
    suspend fun getExercises(ids: Set<ExerciseId>): Map<ExerciseId, Exercise>
}
