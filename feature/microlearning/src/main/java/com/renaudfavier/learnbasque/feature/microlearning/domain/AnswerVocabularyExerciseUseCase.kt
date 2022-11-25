
package com.renaudfavier.learnbasque.feature.microlearning.domain

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import kotlinx.datetime.Clock
import javax.inject.Inject

class AnswerVocabularyExerciseUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val userAnswerRepository: UserAnswerRepository,
) {

    sealed interface Response {
        data class Success(
            val isCorrect: Boolean,
            val correction: QuestionAnswer.AnswerString,
        ): Response

        object Error: Response
    }

    suspend operator fun invoke(
        exercise: Exercise,
        userAnswer: QuestionAnswer.AnswerString,
    ): Response {

        val correction = exercise.getCorrection() ?: return Response.Error
        val isCorrect = userAnswer == correction

        userAnswerRepository.addAnswer(
            UserAnswer(
                exerciseId = exercise.id,
                answer = userAnswer,
                isCorrect = userAnswer == correction,
                date = Clock.System.now()
            )
        )

        return Response.Success(isCorrect, correction)
    }

    private suspend fun Exercise.getCorrection() = when(this) {
        is Exercise.TranslateFromBasque -> wordsRepository.getWord(wordId)?.french?.let {
            QuestionAnswer.AnswerString(it)
        }
        is Exercise.TranslateToBasque -> wordsRepository.getWord(wordId)?.basque?.let {
            QuestionAnswer.AnswerString(it)
        }
    }
}
