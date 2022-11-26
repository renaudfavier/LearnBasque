package com.renaudfavier.learnbasque.core.model.data

import com.renaudfavier.learnbasque.core.model.data.util.Id
import com.renaudfavier.learnbasque.core.model.data.util.toId

typealias ExerciseId = Id<Exercise>

sealed interface Exercise {

    val id: ExerciseId

    data class TranslateFromBasque(
        val wordId: WordId,
        val difficulty: Difficulty,
    ): Exercise {
        override val id: ExerciseId
            get() = "$ID_PREFIX-$wordId-${difficulty.name}".toId()

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

        enum class Difficulty {
            TwoPropositions
        }

        companion object {
            const val ID_PREFIX = "TranslateToBasque"
        }
    }
}
