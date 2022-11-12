package com.renaudfavier.learnbasque.core.testing.repository

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Word

class TestWordsRepository: WordsRepository {

    var words: List<Word> = emptyList()

    override suspend fun getWords(): List<Word> = words

    override suspend fun getWord(wordId: String): Word? {
        TODO("Not yet implemented")
    }

    /**
     * A test-only API to allow setting/unsetting of words.
     *
     */
    fun setupNextWords(words: List<Word>) {
        this.words = words
    }
}
