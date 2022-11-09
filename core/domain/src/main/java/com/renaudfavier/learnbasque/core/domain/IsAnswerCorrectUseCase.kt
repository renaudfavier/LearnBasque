package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.QuestionAnswer
import javax.inject.Inject

class IsAnswerCorrectUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend operator fun invoke(
        exercise: Exercise.TranslateFromBasque,
        questionAnswer: QuestionAnswer.AnswerString
    ) = wordsRepository.getWord(exercise.wordId)?.french == questionAnswer.answer

    suspend operator fun invoke(
        exercise: Exercise.TranslateToBasque,
        questionAnswer: QuestionAnswer.AnswerString
    ) = wordsRepository.getWord(exercise.wordId)?.basque == questionAnswer.answer

}
