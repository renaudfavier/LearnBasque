package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.ExerciseRepository
import com.renaudfavier.learnbasque.core.data.repository.IdBasedExerciseRepository
import com.renaudfavier.learnbasque.core.data.repository.fake.FakeWordRepository
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Knowledge
import com.renaudfavier.learnbasque.core.testing.repository.TestUserAnswerRepository
import com.renaudfavier.learnbasque.core.testing.repository.TestWordsRepository
import com.renaudfavier.learnbasque.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class GetUserKnowledgeSetUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userAnswerRepository = TestUserAnswerRepository()
    private val wordsRepository = TestWordsRepository()
    private val exerciseRepository = IdBasedExerciseRepository()

    val useCase = GetUserKnowledgeSetUseCase(
        exerciseRepository = exerciseRepository,
        answerRepository = userAnswerRepository,
        wordRepository = wordsRepository,
    )

    @Test
    fun whenNoData_EmptyKnowledgeSetIsReturned() = runTest {

        userAnswerRepository.setupNextAnswers(emptyList())
        wordsRepository.setupNextWords(emptyList())

        val result = useCase()

        assertEquals(emptyList<Knowledge>(), result)
    }
}

