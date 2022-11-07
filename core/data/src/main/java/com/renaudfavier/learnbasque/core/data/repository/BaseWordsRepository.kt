package com.renaudfavier.learnbasque.core.data.repository

import com.renaudfavier.learnbasque.core.model.data.Word

class BaseWordsRepository: WordsRepository {

    override suspend fun getWords(): List<Word> = words

    override suspend fun getWord(wordId: String): Word? = words.find { it.id == wordId }
}

private val words = listOf(
    Word(
        id = "baseWords1",
        french = "Oui",
        basque = "Bai",
        complexity = 1
    ),
    Word(
        id = "baseWords2",
        french = "Non",
        basque = "Ez",
        complexity = 1
    ),
    Word(
        id = "baseWords3",
        french = "S’il vous plaît",
        basque = "Otoi / Plazer baduzu",
        complexity = 1
    ),
    Word(
        id = "baseWords4",
        french = "Excusez-moi",
        basque = "Barkatu",
        complexity = 1
    ),
    Word(
        id = "baseWords5",
        french = "Merci",
        basque = "Milesker",
        complexity = 1
    ),
    Word(
        id = "baseWords6",
        french = "Merci beaucoup",
        basque = "Milesker anitz",
        complexity = 1
    ),
    Word(
        id = "baseWords7",
        french = "De rien",
        basque = "Deusetaz",
        complexity = 1
    ),
    Word(
        id = "baseWords8",
        french = "Pardon",
        basque = "Barkatu",
        complexity = 1
    ),
    Word(
        id = "baseWords9",
        french = "Je ne sais pas",
        basque = "Ez dakit",
        complexity = 1
    ),
    Word(
        id = "baseWords10",
        french = "Bienvenue",
        basque = "Ongi etorri",
        complexity = 1
    ),
    Word(
        id = "baseWords11",
        french = "Peut-être",
        basque = "Behar bada",
        complexity = 1
    ),
    Word(
        id = "baseWords12",
        french = "Où",
        basque = "Nun",
        complexity = 1
    ),
    Word(
        id = "baseWords13",
        french = "Comment",
        basque = "Nola",
        complexity = 1
    ),
    Word(
        id = "baseWords14",
        french = "Pourquoi",
        basque = "Zergatik",
        complexity = 1
    ),
)
