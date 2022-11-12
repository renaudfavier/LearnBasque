package com.renaudfavier.learnbasque.core.model.data

sealed class Exercise(open val id: String) {
    data class TranslateFromBasque(
        val wordId: String,
        val difficulty: Difficulty,
    ): Exercise("$ID_PREFIX-$wordId-${difficulty.name}") {

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
    ): Exercise("$ID_PREFIX-$wordId-${difficulty.name}") {

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

    abstract fun maxMastering() : Float
}
