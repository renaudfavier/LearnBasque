package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Exercise

class IdBasedExerciseRepository: ExerciseRepository {

    override suspend fun getExercise(id: String): Exercise {
        val list = id.split("-")
        when(list.first()) {
            Exercise.TranslateFromBasque.ID_PREFIX ->
                Exercise.TranslateFromBasque(
                    list[1],
                    Exercise.TranslateFromBasque.Difficulty.valueOf(list[2])
                )
            Exercise.TranslateToBasque.ID_PREFIX ->
                Exercise.TranslateToBasque(
                    list[1],
                    Exercise.TranslateToBasque.Difficulty.valueOf(list[2])
                )
        }
    }

    override suspend fun getExercises(ids: Set<String>): Map<String, Exercise> =
        ids.associateWith { getExercise(it) }

}
