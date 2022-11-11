package com.renaudfavier.learnbasque.core.model.data

sealed class Exercise(open val id: String) {
    data class TranslateFromBasque(
        val wordId: String,
        val difficulty: Difficulty,
    ): Exercise("$wordId${difficulty.name}") {
        enum class Difficulty {
            TwoPropositions
        }
    }
    data class TranslateToBasque(
        val wordId: String,
        val difficulty: Difficulty,
    ): Exercise("$wordId${difficulty.name}") {
        enum class Difficulty {
            TwoPropositions
        }
    }
}
