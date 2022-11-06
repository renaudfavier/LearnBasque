package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWordRepository : WordsRepository {

    override fun getWordsStream(): Flow<List<Word>> {
        val words = ArrayList<Word>()
        for (i in 1..10) {
            words.add(
                Word(id = "fake$i", "fr$i", "bask$i")
            )
        }
        return flow { emit(words) }
    }
}
