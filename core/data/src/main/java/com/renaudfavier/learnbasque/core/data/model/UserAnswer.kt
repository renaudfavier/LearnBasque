package com.renaudfavier.learnbasque.core.data.model

import com.renaudfavier.learnbasque.core.database.entity.UserAnswerEntity
import com.renaudfavier.learnbasque.core.database.entity.asString
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.type
import java.util.UUID

fun UserAnswer.toEntity() = UserAnswerEntity(
    id = UUID.randomUUID().toString(),
    questionId = questionId,
    answerAsString = answer.asString(),
    answerType = answer.type(),
    isCorrect = isCorrect,
    date = date
)
