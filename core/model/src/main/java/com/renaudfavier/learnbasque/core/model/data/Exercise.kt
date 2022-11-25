package com.renaudfavier.learnbasque.core.model.data

import com.renaudfavier.learnbasque.core.model.data.util.Id
import com.renaudfavier.learnbasque.core.model.data.util.toId

typealias ExerciseId = Id<Exercise>

sealed interface Exercise {

    val id: ExerciseId
    fun maxMastering() : Float

    data class TranslateFromBasque(
        val wordId: WordId,
        val difficulty: Difficulty,
    ): Exercise {
        override val id: ExerciseId
            get() = "$ID_PREFIX-$wordId-${difficulty.name}".toId()

        override fun maxMastering(): Float = when(difficulty) {
            Difficulty.TwoPropositions -> 0.2f
        }

        enum class Difficulty {
            TwoPropositions
        }

        companion object {
            const val ID_PREFIX = "TranslateFromBasque"
        }
    }

    data class TranslateToBasque(
        val wordId: WordId,
        val difficulty: Difficulty,
    ): Exercise {
        override val id: ExerciseId
            get() = "$ID_PREFIX-$wordId-${difficulty.name}".toId()

        override fun maxMastering(): Float = when(difficulty) {
            Difficulty.TwoPropositions -> 0.3f
        }

        enum class Difficulty {
            TwoPropositions
        }

        companion object {
            const val ID_PREFIX = "TranslateToBasque"
        }
    }
}
