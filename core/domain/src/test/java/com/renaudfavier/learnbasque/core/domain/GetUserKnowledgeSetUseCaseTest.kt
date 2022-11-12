package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.IdBasedExerciseRepository
import com.renaudfavier.learnbasque.core.domain.model.KnowledgeWithMastering
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Exercise.TranslateFromBasque.Difficulty.TwoPropositions
import com.renaudfavier.learnbasque.core.model.data.Knowledge
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import com.renaudfavier.learnbasque.core.model.data.Word
import com.renaudfavier.learnbasque.core.testing.repository.TestUserAnswerRepository
import com.renaudfavier.learnbasque.core.testing.repository.TestWordsRepository
import com.renaudfavier.learnbasque.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertArrayEquals
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat

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

    @Test
    fun whenAllAnswerAreCorrect_maxMasteringIsReturned() = runTest {
        val userAnwers = listOf(
            sampleCorrectUserAnswerFB,
            sampleCorrectUserAnswerFB,
            sampleCorrectUserAnswerFB
        )

        wordsRepository.setupNextWords(listOf(sampleWord))
        userAnswerRepository.setupNextAnswers(userAnwers)

        val result = useCase()

        val knowledgeWithMastering = KnowledgeWithMastering(sampleKnowledge, sampleTranslateFromBasqueTwoPropositionExercise.maxMastering())
        assertEquals(listOf(knowledgeWithMastering), result)
    }

    @Test
    fun whenOnlyLastAnswerIsCorrect_50percentOfMaxMasteringIsReturned() = runTest {
        val userAnwers = listOf(
            sampleCorrectUserAnswerFB,
            sampleWrongUserAnswerFB,
        )

        wordsRepository.setupNextWords(listOf(sampleWord))
        userAnswerRepository.setupNextAnswers(userAnwers)

        val result = useCase()

        val knowledgeWithMastering =
            KnowledgeWithMastering(
                sampleKnowledge,
                sampleTranslateFromBasqueTwoPropositionExercise.maxMastering() * 0.5f
            )
        assertEquals(listOf(knowledgeWithMastering), result)
    }

    @Test
    fun whenLastAnswerIsWrong_0IsReturned() = runTest {
        val userAnwers = listOf(
            sampleCorrectUserAnswerFB,
            sampleWrongUserAnswerFB,
            sampleVeryRecentWrongUserAnswerFB,
        )

        wordsRepository.setupNextWords(listOf(sampleWord))
        userAnswerRepository.setupNextAnswers(userAnwers)

        val result = useCase()

        val knowledgeWithMastering =
            KnowledgeWithMastering(
                sampleKnowledge,
                0f
            )
        assertEquals(listOf(knowledgeWithMastering), result)
    }

    @Test
    fun whenMultitudeExercice() = runTest {
        val userAnwers = listOf(
            sampleCorrectUserAnswerFB,
            sampleWrongUserAnswerFB,
            sampleCorrectUserAnswerFB,
            sampleCorrectUserAnswerTB,
            sampleWrongUserAnswerTB,
            sampleCorrectUserAnswerTB,
        )

        wordsRepository.setupNextWords(listOf(sampleWord))
        userAnswerRepository.setupNextAnswers(userAnwers)

        val result = useCase()

        val knowledgeWithMastering =
            KnowledgeWithMastering(
                sampleKnowledge,
                0.24000001f
            )
        assertEquals(listOf(knowledgeWithMastering), result)
    }

}

private val sampleWord = Word("wordid", "", "", 0)
private val sampleKnowledge = Knowledge.Vocabulary(sampleWord)
private val sampleTranslateFromBasqueTwoPropositionExercise = Exercise.TranslateFromBasque(sampleWord.id, TwoPropositions)
private val sampleTranslateToBasqueTwoPropositionExercise = Exercise.TranslateToBasque(sampleWord.id, Exercise.TranslateToBasque.Difficulty.TwoPropositions)

private val sampleCorrectUserAnswerFB = UserAnswer(
    sampleTranslateFromBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    true,
    Instant.parse("2021-11-08T00:00:00.000Z"),
)
private val sampleWrongUserAnswerFB = UserAnswer(
    sampleTranslateFromBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    false,
    Instant.parse("2021-11-07T00:00:00.000Z"),
)
private val sampleVeryRecentWrongUserAnswerFB = UserAnswer(
    sampleTranslateFromBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    false,
    Instant.parse("2021-11-10T00:00:00.000Z"),
)
private val sampleCorrectUserAnswerTB = UserAnswer(
    sampleTranslateToBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    true,
    Instant.parse("2021-11-08T00:00:00.000Z"),
)
private val sampleWrongUserAnswerTB = UserAnswer(
    sampleTranslateToBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    false,
    Instant.parse("2021-11-07T00:00:00.000Z"),
)
private val sampleVeryRecentWrongUserAnswerTB = UserAnswer(
    sampleTranslateToBasqueTwoPropositionExercise.id,
    QuestionAnswer.AnswerString(""),
    false,
    Instant.parse("2021-11-10T00:00:00.000Z"),
)
