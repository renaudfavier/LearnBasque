package com.renaudfavier.learnbasque.core.model.data

import com.renaudfavier.learnbasque.core.model.data.util.Id
import com.renaudfavier.learnbasque.core.model.data.util.toId

typealias LessonId = Id<Lesson>

sealed interface Lesson {

    val id: LessonId

    data class NewWord(
        val wordId: WordId,
    ): Lesson {
        override val id: LessonId
            get() = "$ID_PREFIX-$wordId".toId()

        companion object {
            const val ID_PREFIX = "NewWord"
        }
    }
}
