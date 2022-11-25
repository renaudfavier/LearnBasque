package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.ExerciseId
import com.renaudfavier.learnbasque.core.model.data.util.toId

class IdBasedExerciseRepository: ExerciseRepository {

    override suspend fun getExercise(id: ExerciseId): Exercise {
        val list = id.raw.split("-")
        return when(list.first()) {
            Exercise.TranslateFromBasque.ID_PREFIX ->
                Exercise.TranslateFromBasque(
                    list[1].toId(),
                    Exercise.TranslateFromBasque.Difficulty.valueOf(list[2])
                )
            Exercise.TranslateToBasque.ID_PREFIX ->
                Exercise.TranslateToBasque(
                    list[1].toId(),
                    Exercise.TranslateToBasque.Difficulty.valueOf(list[2])
                )
            else -> TODO()
        }
    }

    override suspend fun getExercises(ids: Set<ExerciseId>): Map<ExerciseId, Exercise> =
        ids.associateWith { getExercise(it) }

}
