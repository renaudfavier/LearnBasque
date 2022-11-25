package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.model.KnowledgeWithMastering
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Knowledge
import com.renaudfavier.learnbasque.core.model.data.Lesson
import javax.inject.Inject

class GetBestExerciseToTryNextUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val getUserKnowledgeSetUseCase: GetUserKnowledgeSetUseCase,
    private val answerRepository: UserAnswerRepository,
) {

    sealed interface LearningUnit {

        data class Exo(val exercise: Exercise) : LearningUnit
        data class Less(val lesson: Lesson) : LearningUnit
    }

    /***
        - A User has a set of knowledge
        - A User has a certain degree of mastering of each knowledge
        - An exercise is meant to reinforce one or more knowledge
        - The degree of mastering of a knowledge can be determined
            by looking at the result of previous exercises about the knowledge
        - The same knowledge can be reinforced by a multitude of exercise
        - The best exercise is the one that will reinforce the most the knowledge
        - The right knowledge to reinforce is the one that will be cost efficient to reinforce
     */

    suspend operator fun invoke(): LearningUnit {

        val userKnowledge = getUserKnowledgeSetUseCase()

        val bestExercise = userKnowledge
            .mapNotNull { it.getBestExercise()?.let { exo -> Pair(it, exo) } }
            .filter { (userKnowledge, bestExercise) ->
                bestExercise.maxMastering() - (userKnowledge.mastering ?: 0f) > 0.05f
            }.maxByOrNull { (userKnowledge, bestExercise) ->
                bestExercise.maxMastering() - (userKnowledge.mastering ?: 0f)
            }
            ?.second

        return bestExercise?.let { LearningUnit.Exo(it) } ?: run {
            val newWord = wordsRepository.getWords()
                .filter { word -> userKnowledge.none { it.knowledge == Knowledge.Vocabulary(word) } }
                .random()
            LearningUnit.Less(Lesson.NewWord(newWord.id))
        }
    }

    private fun KnowledgeWithMastering.getBestExercise() = when (this.knowledge) {
        is Knowledge.Vocabulary -> knowledge.getBestVocabularyExercise(this.mastering)
    }

    private fun Knowledge.Vocabulary.getBestVocabularyExercise(mastering: Float?) = when {
        mastering == null -> null
        mastering < 0.08f -> null
        mastering < 0.15f -> Exercise.TranslateFromBasque(this.word.id, Exercise.TranslateFromBasque.Difficulty.TwoPropositions)
        else ->  Exercise.TranslateToBasque(this.word.id, Exercise.TranslateToBasque.Difficulty.TwoPropositions)
    }

}
