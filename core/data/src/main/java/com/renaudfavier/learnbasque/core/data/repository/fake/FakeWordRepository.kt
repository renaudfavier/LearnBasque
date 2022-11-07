package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Word

class FakeWordRepository : WordsRepository {

    private val words: MutableList<Word> = mutableListOf()

    init {
        for (i in 1..10) {
            words.add(
                Word("fake$i", "fr$i", "bask$i", i)
            )
        }
    }

    override suspend fun getWords(): List<Word> = words

    override suspend fun getWord(wordId: String): Word? = words.find { it.id == wordId }

}
