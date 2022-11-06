package com.renaudfavier.learnbasque.core.data.repository.fake

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWordRepository : WordsRepository {

    override fun getWordsStream(): Flow<List<Word>> {
        return flow {
            emit(
                listOf(
                    Word(id = "", "jambon", "jamon"),
                    Word(id = "", "jambon2", "jamon2"),
                )
            )
        }
    }
}
