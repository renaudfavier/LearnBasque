package com.renaudfavier.learnbasque.core.model.data

sealed interface Knowledge {
    data class Vocabulary(
        val word: Word,
    ): Knowledge
}
