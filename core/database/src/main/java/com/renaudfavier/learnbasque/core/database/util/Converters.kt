package com.renaudfavier.learnbasque.core.database.util

import androidx.room.TypeConverter
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswerType
import kotlinx.datetime.Instant

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()

    @TypeConverter
    fun questionAnswerTypeToString(questionAnswerType: QuestionAnswerType) = questionAnswerType.name

    @TypeConverter
    fun stringTypeToQuestionAnswer(value: String): QuestionAnswerType = enumValueOf(value)
}
