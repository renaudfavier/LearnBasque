package com.renaudfavier.learnbasque.core.model.data

import com.renaudfavier.learnbasque.core.model.data.util.Id

typealias WordId = Id<Word>

data class Word(
    val id: WordId,
    val french: String,
    val basque: String,
    val complexity: Int,
)

