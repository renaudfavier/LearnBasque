package com.renaudfavier.learnbasque.core.model.data

import kotlinx.datetime.Instant

data class UserAnswer(
    val questionId: String,
    val answer: QuestionAnswer,
    val isCorrect: Boolean,
    val date: Instant,
)

sealed interface QuestionAnswer {
    class AnswerString(val answer: String): QuestionAnswer
}

enum class QuestionAnswerType {
    String
}

fun QuestionAnswer.type() = when(this) {
    is QuestionAnswer.AnswerString -> QuestionAnswerType.String
}
