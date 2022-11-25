package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.IdBasedExerciseRepository
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.testing.model.testNewWordExercise
import com.renaudfavier.learnbasque.core.testing.model.testTranslateFromBasqueExercise
import com.renaudfavier.learnbasque.core.testing.model.testTranslateToBasqueExercise
import com.renaudfavier.learnbasque.core.testing.model.testUserAnswer
import com.renaudfavier.learnbasque.core.testing.model.testWord
import com.renaudfavier.learnbasque.core.testing.repository.TestUserAnswerRepository
import com.renaudfavier.learnbasque.core.testing.repository.TestWordsRepository
import com.renaudfavier.learnbasque.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class GetBestExerciceToTryNextUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val wordsRepository = TestWordsRepository()
    private val userAnswerRepository = TestUserAnswerRepository()
    private val exerciseRepository = IdBasedExerciseRepository()
    private val getUserKnowledgeSetUseCase = GetUserKnowledgeSetUseCase(
        exerciseRepository, userAnswerRepository, wordsRepository
    )

    val useCase = GetBestExerciseToTryNextUseCase(
        wordsRepository = wordsRepository,
        getUserKnowledgeSetUseCase = getUserKnowledgeSetUseCase,
        answerRepository = userAnswerRepository,
    )

    @Test
    fun whenNoData_NewWordIsReturned() = runTest {

        val sampleWord1 = testWord(id = "1")
        wordsRepository.setupNextWords(listOf(sampleWord1))
        userAnswerRepository.setupNextAnswers(emptyList())

        val result = useCase()

        assertEquals(result, Exercise.NewWord("1"))
    }

    @Test
    fun whenFailedAnswer_SameAnswerIsProposed() = runTest {

         val sampleWord1 = testWord(id = "1")
         val sampleWord2 = testWord(id = "2")
        wordsRepository.setupNextWords(listOf(sampleWord1, sampleWord2))

         val sampleNewWord1 = testNewWordExercise(sampleWord1.id)
         val sampleNewWord2 = testNewWordExercise(sampleWord2.id)
         val sampleExercise1 = testTranslateFromBasqueExercise(sampleWord1.id)
         val sampleExercise2 = testTranslateFromBasqueExercise(sampleWord2.id)

        val userAnswers = listOf(
            testUserAnswer(exerciseId = sampleNewWord1.id, isCorrect = true),
            testUserAnswer(exerciseId = sampleNewWord2.id, isCorrect = true),
            testUserAnswer(exerciseId = sampleExercise1.id, isCorrect = true),
            testUserAnswer(exerciseId = sampleExercise1.id, isCorrect = true),
            testUserAnswer(exerciseId = sampleExercise1.id, isCorrect = true),
            testUserAnswer(exerciseId = sampleExercise2.id, isCorrect = false),
            testUserAnswer(exerciseId = sampleExercise2.id, isCorrect = true),
        )
        userAnswerRepository.setupNextAnswers(userAnswers)


        val result = useCase()

        assertEquals(testTranslateToBasqueExercise(sampleWord1.id), result)
    }

    @Test
    fun whenKnowledgeIsMaxed_NewWord() = runTest {

        val sampleWord1 = testWord(id = "1")
        val sampleWord2 = testWord(id = "2")
        wordsRepository.setupNextWords(listOf(sampleWord1, sampleWord2))

        // Most difficult Exercise
        val sampleExercise1 = testTranslateToBasqueExercise(sampleWord1.id)

        // User last 3 answers were correct to the most difficult exercise
        val sampleCorrectAnswer = testUserAnswer(exerciseId = sampleExercise1.id, isCorrect = true)
        userAnswerRepository.setupNextAnswers(listOf(sampleCorrectAnswer, sampleCorrectAnswer, sampleCorrectAnswer))

        val result = useCase()

        // We return a new word
        assertEquals(Exercise.NewWord(sampleWord2.id), result)
    }

}
