package com.renaudfavier.learnbasque.core.model.data

sealed interface Exercise {

    val id: String
    fun maxMastering() : Float

    data class NewWord(
        val wordId: String,
    ): Exercise {
        override val id: String
            get() = "$ID_PREFIX-$wordId"

        override fun maxMastering(): Float = 0.1f

        companion object {
            const val ID_PREFIX = "NewWord"
        }
    }

    data class TranslateFromBasque(
        val wordId: String,
        val difficulty: Difficulty,
    ): Exercise {
        override val id: String
            get() = "$ID_PREFIX-$wordId-${difficulty.name}"

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
        val wordId: String,
        val difficulty: Difficulty,
    ): Exercise {
        override val id: String
            get() = "$ID_PREFIX-$wordId-${difficulty.name}"

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
