package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.ExerciseRepository
import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.model.KnowledgeWithMastering
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Knowledge
import com.renaudfavier.learnbasque.core.model.data.UserAnswer
import kotlinx.datetime.Instant
import javax.inject.Inject

/***
- A User has a set of knowledge
- A User has a certain degree of mastering of each knowledge
- An exercise is meant to reinforce one or more knowledge
- The degree of mastering of a knowledge can be determined
by looking at the result of previous exercises about the knowledge
 */

class GetUserKnowledgeSetUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val answerRepository: UserAnswerRepository,
    private val wordRepository: WordsRepository
) {
    suspend operator fun invoke(): List<KnowledgeWithMastering> = answerRepository
            .getAnswers()
            .groupBy { it.questionId }
            .mapKeys { exerciseRepository.getExercise(it.key) }
            .groupByKnowledge()
            .map { (exercise, userAnswers) ->
                KnowledgeWithMastering(
                    exercise,
                    userAnswers
                        .sortedBy { exerciseWithUserData ->  exerciseWithUserData.exercise.maxMastering() }
                        .computeMastering()
                )
            }

    private suspend fun Map<Exercise, List<UserAnswer>>.groupByKnowledge(): Map<Knowledge, List<ExerciseWithUserData>> {
        val returnMap: MutableMap<Knowledge, MutableList<ExerciseWithUserData>> = mutableMapOf()

        this.forEach { (exercise, userAnswers) ->
            val knowledge: Knowledge = exercise.getKnowledge() ?: return@forEach
            if(returnMap[knowledge] == null) { returnMap[knowledge] = mutableListOf() }
            returnMap[knowledge]!!.add(ExerciseWithUserData(exercise, userAnswers.toThreeMostRecentUserAnswer()))
        }

        return returnMap
    }

    private suspend fun Exercise.getKnowledge() =
        when(this) {
            is Exercise.NewWord -> wordRepository.getWord(wordId)?.let { Knowledge.Vocabulary(it) }
            is Exercise.TranslateFromBasque -> wordRepository.getWord(wordId)?.let { Knowledge.Vocabulary(it) }
            is Exercise.TranslateToBasque -> wordRepository.getWord(wordId)?.let { Knowledge.Vocabulary(it) }
        }

    /**
     * You can't get more than the max mastering of an exercise from suceeding it
     * when last 3 tries are success you get 100%
     * last 2 tries you get 80%
     * last try you get 50%
     * if it's > 1 day old you get 10% malus
     * if > 1 week old 20% malus
     * if > 1 month old 30% malus
     */
    private fun List<ExerciseWithUserData>.computeMastering(): Float? {
        var returnedMastering: Float? = null

        var mostRecentSuccessDate: Instant? = null
        var mostRecentFailDate: Instant? = null
        var mostRecentFailMaxMastering: Float? = null

        this.forEach {
            val maxMastering = it.exercise.maxMastering()
            if(it.userAnswers.mostRecentAnswer == null) return@forEach

            if(it.userAnswers.mostRecentAnswer.isCorrect) {
                if (returnedMastering != null && returnedMastering!! > maxMastering) {
                    return@forEach
                }
                if (mostRecentSuccessDate == null || it.userAnswers.mostRecentAnswer.date > mostRecentSuccessDate!!) {
                    mostRecentSuccessDate = it.userAnswers.mostRecentAnswer.date
                }

                returnedMastering = maxMastering / 2
                if (it.userAnswers.secondMostRecentAnswer?.isCorrect == true) {
                    returnedMastering = returnedMastering!! + maxMastering * 0.3f
                }
                if (it.userAnswers.thirdMostRecentAnswer?.isCorrect == true) {
                    returnedMastering = returnedMastering!! + maxMastering * 0.2f
                }

                //TODO Apply date malus


            } else {
                if (mostRecentFailDate == null || it.userAnswers.mostRecentAnswer.date > mostRecentFailDate!!) {
                    mostRecentFailDate = it.userAnswers.mostRecentAnswer.date
                    mostRecentFailMaxMastering = maxMastering
                }
            }

        }
        return when {
            mostRecentSuccessDate == null && mostRecentFailDate == null -> null
            mostRecentSuccessDate == null || (mostRecentFailDate ?: Instant.DISTANT_PAST) > mostRecentSuccessDate!! ->
                Math.min(returnedMastering ?: 0f, mostRecentFailMaxMastering ?: 1f)
            else -> returnedMastering
        }
    }

    private class ExerciseWithUserData(
        val exercise: Exercise,
        val userAnswers: ThreeMostRecentUserAnswer
    )

    private fun List<UserAnswer>.toThreeMostRecentUserAnswer() =
        sortedByDescending { it.date }
            .run {
                ThreeMostRecentUserAnswer(
                    getOrNull(0),
                    getOrNull(1),
                    getOrNull(2),
                )
            }

    private data class ThreeMostRecentUserAnswer(
        val mostRecentAnswer: UserAnswer?,
        val secondMostRecentAnswer: UserAnswer?,
        val thirdMostRecentAnswer: UserAnswer?,
    )

}
