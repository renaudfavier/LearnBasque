package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.IdBasedExerciseRepository
import com.renaudfavier.learnbasque.core.model.data.Lesson
import com.renaudfavier.learnbasque.core.model.data.util.toId
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

    val useCase = GetNextLearningUnitUseCase(
        wordsRepository = wordsRepository,
        getUserKnowledgeSetUseCase = getUserKnowledgeSetUseCase,
    )

    @Test
    fun whenNoData_NewWordIsReturned() = runTest {

        val sampleWord1 = testWord(id = "1".toId())
        wordsRepository.setupNextWords(listOf(sampleWord1))
        userAnswerRepository.setupNextAnswers(emptyList())

        val result = useCase()

        assertEquals(
            result,
            GetNextLearningUnitUseCase.LearningUnit.Less(
                Lesson.NewWord(sampleWord1.id)
            )
        )
    }

    @Test
    fun whenFailedAnswer_SameAnswerIsProposed() = runTest {

         val sampleWord1 = testWord(id = "1".toId())
         val sampleWord2 = testWord(id = "2".toId())
        wordsRepository.setupNextWords(listOf(sampleWord1, sampleWord2))

         val sampleExercise1 = testTranslateFromBasqueExercise(sampleWord1.id)
         val sampleExercise2 = testTranslateFromBasqueExercise(sampleWord2.id)

        val userAnswers = listOf(
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

}
