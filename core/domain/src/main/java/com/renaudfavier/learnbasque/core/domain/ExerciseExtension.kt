package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.model.data.Exercise

internal fun Exercise.maxMastering() = when(this) {
    is Exercise.TranslateFromBasque -> this.maxMastering()
    is Exercise.TranslateToBasque -> this.maxMastering()
}

private fun Exercise.TranslateFromBasque.maxMastering() = when(this.difficulty) {
    Exercise.TranslateFromBasque.Difficulty.TwoPropositions -> 0.3f
}

private fun Exercise.TranslateToBasque.maxMastering() = when(this.difficulty) {
    Exercise.TranslateToBasque.Difficulty.TwoPropositions -> 0.4f
}
