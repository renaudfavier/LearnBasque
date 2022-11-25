package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.model.data.WordId
import com.renaudfavier.learnbasque.core.model.data.util.toId

class BaseWordsRepository: WordsRepository {

    override suspend fun getWords(): List<Word> = words

    override suspend fun getWord(wordId: WordId): Word? = words.find { it.id == wordId }
}

private val words = listOf(
    Word(
        id = "baseWords1".toId(),
        french = "Oui",
        basque = "Bai",
        complexity = 1
    ),
    Word(
        id = "baseWords2".toId(),
        french = "Non",
        basque = "Ez",
        complexity = 1
    ),
    Word(
        id = "baseWords3".toId(),
        french = "S’il vous plaît",
        basque = "Otoi / Plazer baduzu",
        complexity = 1
    ),
    Word(
        id = "baseWords4".toId(),
        french = "Excusez-moi",
        basque = "Barkatu",
        complexity = 1
    ),
    Word(
        id = "baseWords5".toId(),
        french = "Merci",
        basque = "Milesker",
        complexity = 1
    ),
    Word(
        id = "baseWords6".toId(),
        french = "Merci beaucoup",
        basque = "Milesker anitz",
        complexity = 1
    ),
    Word(
        id = "baseWords7".toId(),
        french = "De rien",
        basque = "Deusetaz",
        complexity = 1
    ),
    Word(
        id = "baseWords8".toId(),
        french = "Pardon",
        basque = "Barkatu",
        complexity = 1
    ),
    Word(
        id = "baseWords9".toId(),
        french = "Je ne sais pas",
        basque = "Ez dakit",
        complexity = 1
    ),
    Word(
        id = "baseWords10".toId(),
        french = "Bienvenue",
        basque = "Ongi etorri",
        complexity = 1
    ),
    Word(
        id = "baseWords11".toId(),
        french = "Peut-être",
        basque = "Behar bada",
        complexity = 1
    ),
    Word(
        id = "baseWords12".toId(),
        french = "Où",
        basque = "Nun",
        complexity = 1
    ),
    Word(
        id = "baseWords13".toId(),
        french = "Comment",
        basque = "Nola",
        complexity = 1
    ),
    Word(
        id = "baseWords14".toId(),
        french = "Pourquoi",
        basque = "Zergatik",
        complexity = 1
    ),
)
