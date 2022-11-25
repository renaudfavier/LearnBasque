package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.model.data.WordId
import com.renaudfavier.learnbasque.core.model.data.util.toId

class FakeWordRepository : WordsRepository {

    private val words: MutableList<Word> = mutableListOf()

    init {
        for (i in 1..10) {
            words.add(
                Word("fake$i".toId(), "fr$i", "bask$i", i)
            )
        }
    }

    override suspend fun getWords(): List<Word> = words

    override suspend fun getWord(wordId: WordId): Word? = words.find { it.id == wordId }

}
