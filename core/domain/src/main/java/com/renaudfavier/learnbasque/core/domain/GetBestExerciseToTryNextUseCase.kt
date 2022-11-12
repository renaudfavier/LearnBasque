package com.renaudfavier.learnbasque.core.domain

import com.renaudfavier.learnbasque.core.data.repository.UserAnswerRepository
import javax.inject.Inject

class GetBestExerciseToTryNextUseCase @Inject constructor(
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

    private suspend fun getKnowledgeSet() {

    }


}
