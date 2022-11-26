package com.renaudfavier.learnbasque.core.testing.model

import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.ExerciseId
import com.renaudfavier.learnbasque.core.model.data.Lesson
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.model.data.WordId
import com.renaudfavier.learnbasque.core.model.data.util.toId
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


fun testWord(
    id: WordId = "".toId(),
    french: String = "",
    basque: String = "",
    complexity: Int = 0,
) = Word(
    id = id,
    french = french,
    basque = basque,
    complexity = complexity,
)

fun testNewWordExercise(id: WordId = "".toId()) = Lesson.NewWord(wordId = id)

fun testTranslateFromBasqueExercise(
    id: WordId = "".toId(),
    difficulty: Exercise.TranslateFromBasque.Difficulty = Exercise.TranslateFromBasque.Difficulty.TwoPropositions
) = Exercise.TranslateFromBasque(wordId = id, difficulty = difficulty)

fun testTranslateToBasqueExercise(
    id: WordId = "".toId(),
    difficulty: Exercise.TranslateToBasque.Difficulty = Exercise.TranslateToBasque.Difficulty.TwoPropositions
) = Exercise.TranslateToBasque(wordId = id, difficulty = difficulty)

fun testUserAnswer(
    exerciseId: ExerciseId = "".toId(),
    answer: QuestionAnswer = QuestionAnswer.AnswerString(""),
    isCorrect: Boolean = true,
    date: Instant = Clock.System.now()
) = UserAnswer(exerciseId = exerciseId, answer = answer, isCorrect =  isCorrect, date = date)
