package com.renaudfavier.learnbasque.core.model.data

import kotlinx.datetime.Instant

data class MemoryTestAnswer(
    val wordId: String,
    val isCorrect: Boolean,
    val date: Instant,
)
