package com.renaudfavier.learnbasque.core.testing.model

import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


fun testWord(
    id: String = "",
    french: String = "",
    basque: String = "",
    complexity: Int = 0,
) = Word(
    id = id,
    french = french,
    basque = basque,
    complexity = complexity,
)

fun testNewWordExercise(id: String = "") = Exercise.NewWord(wordId = id)

fun testTranslateFromBasqueExercise(
    id: String = "",
    difficulty: Exercise.TranslateFromBasque.Difficulty = Exercise.TranslateFromBasque.Difficulty.TwoPropositions
) = Exercise.TranslateFromBasque(wordId = id, difficulty = difficulty)

fun testTranslateToBasqueExercise(
    id: String = "",
    difficulty: Exercise.TranslateToBasque.Difficulty = Exercise.TranslateToBasque.Difficulty.TwoPropositions
) = Exercise.TranslateToBasque(wordId = id, difficulty = difficulty)

fun testUserAnswer(
    questionId: String = "",
    answer: QuestionAnswer = QuestionAnswer.AnswerString(""),
    isCorrect: Boolean = true,
    date: Instant = Clock.System.now()
) = UserAnswer(questionId = questionId, answer = answer, isCorrect =  isCorrect, date = date)
