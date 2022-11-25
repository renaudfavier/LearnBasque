package com.renaudfavier.learnbasque.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswerType
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.util.toId
import kotlinx.datetime.Instant

@Entity(
    tableName = USER_ANSWER_TABLE_NAME,
    indices = [Index(value = ["exerciseId"])]
)
data class UserAnswerEntity(
    @PrimaryKey
    val id: String,
    val exerciseId: String,
    val answerAsString: String,
    val answerType: QuestionAnswerType,
    val isCorrect: Boolean,
    val date: Instant,
)

fun UserAnswerEntity.asExternalModel() = UserAnswer(
    exerciseId = exerciseId.toId(),
    answer = answerAsString.parse(answerType),
    isCorrect = isCorrect,
    date = date
)

private fun String.parse(type: QuestionAnswerType) = when(type) {
    QuestionAnswerType.String -> QuestionAnswer.AnswerString(this)
}

fun QuestionAnswer.asString() = when(this) {
    is QuestionAnswer.AnswerString -> this.answer
}

const val USER_ANSWER_TABLE_NAME = "user_answers"
