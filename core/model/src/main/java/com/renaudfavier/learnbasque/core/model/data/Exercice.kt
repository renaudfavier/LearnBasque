package com.renaudfavier.learnbasque.core.model.data

sealed interface Exercise {
    data class TranslateFromBasque(
        val wordId: String
    ): Exercise
    data class TranslateToBasque(
        val wordId: String
    ): Exercise
}
