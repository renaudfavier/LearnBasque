package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Exercise

interface ExerciseRepository {

    suspend fun getExercise(id: String): Exercise
    suspend fun getExercises(ids: Set<String>): Map<String, Exercise>
}
