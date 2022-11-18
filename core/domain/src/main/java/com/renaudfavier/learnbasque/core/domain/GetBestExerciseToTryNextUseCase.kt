package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import com.renaudfavier.learnbasque.core.data.repository.WordsRepository
import com.renaudfavier.learnbasque.core.domain.model.KnowledgeWithMastering
import com.renaudfavier.learnbasque.core.model.data.Exercise
import com.renaudfavier.learnbasque.core.model.data.Knowledge
import javax.inject.Inject

class GetBestExerciseToTryNextUseCase @Inject constructor(
    private val wordsRepository: WordsRepository,
    private val getUserKnowledgeSetUseCase: GetUserKnowledgeSetUseCase,
    private val answerRepository: UserAnswerRepository,
) {

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

    suspend operator fun invoke(): Exercise {

        val userKnowledge = getUserKnowledgeSetUseCase()

        val bestExercise = userKnowledge
            .map { Pair(it, it.getBestExercise()) }
            .filter { (userKnowledge, bestExercise) ->
                bestExercise.maxMastering() - (userKnowledge.mastering ?: 0f) > 0.05f
            }.maxByOrNull { (userKnowledge, bestExercise) ->
                bestExercise.maxMastering() - (userKnowledge.mastering ?: 0f)
            }
            ?.second

        return bestExercise ?: run {
            val newWord = wordsRepository.getWords()
                .filter { word -> userKnowledge.none { it.knowledge == Knowledge.Vocabulary(word) } }
                .random()
            Exercise.NewWord(newWord.id)
        }
    }

    private fun KnowledgeWithMastering.getBestExercise() = when (this.knowledge) {
        is Knowledge.Vocabulary -> knowledge.getBestVocabularyExercise(this.mastering)
    }

    private fun Knowledge.Vocabulary.getBestVocabularyExercise(mastering: Float?) = when {
        mastering == null -> Exercise.NewWord(this.word.id)
        mastering < 0.08f -> Exercise.NewWord(this.word.id)
        mastering < 0.15f -> Exercise.TranslateFromBasque(this.word.id, Exercise.TranslateFromBasque.Difficulty.TwoPropositions)
        else ->  Exercise.TranslateToBasque(this.word.id, Exercise.TranslateToBasque.Difficulty.TwoPropositions)
    }

}
