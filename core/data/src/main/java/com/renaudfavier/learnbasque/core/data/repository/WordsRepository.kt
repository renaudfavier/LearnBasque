package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    /**
     * Returns available words resources as a stream.
     */
    fun getWordsStream(): Flow<List<Word>>
}
