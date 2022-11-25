package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.model.data.WordId
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    suspend fun getWords(): List<Word>

    suspend fun getWord(wordId: WordId): Word?
}
