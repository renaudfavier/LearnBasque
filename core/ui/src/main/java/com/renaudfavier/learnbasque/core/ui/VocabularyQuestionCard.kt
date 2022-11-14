package com.renaudfavier.learnbasque.core.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.renaudfavier.learnbasque.core.designsystem.theme.BlueGray
import com.renaudfavier.learnbasque.core.designsystem.theme.CorrectGreen
import com.renaudfavier.learnbasque.core.designsystem.theme.IncorrectRed
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.designsystem.theme.NeutralGray
import com.renaudfavier.learnbasque.core.designsystem.theme.PurpleGrey80
import com.renaudfavier.learnbasque.core.designsystem.theme.Typography

@Composable
fun VocabularyQuestionCard(
    model: VocabularyQuestionCardConfiguration,
    onPropositionTap : (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = model.wordToTranslate,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ),
            style = Typography.displayMedium,
        )
        FlowRow(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp

        ) {
            model.propositions.forEachIndexed { index, proposition ->
                val animatedColor by animateColorAsState(
                    targetValue = proposition.backgroundColor
                )
                Button(
                    onClick = { onPropositionTap(index) },
                    colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                ) {
                    Text(text = proposition.value, modifier = Modifier.padding(3.dp))
                }
            }
        }
    }
}

data class VocabularyQuestionCardConfiguration(
    val wordToTranslate: String,
    val propositions: List<Proposition>,
) {
    data class Proposition(
        val value:String,
        val backgroundColor: Color
    )
}

@DevicePreviews
@Composable
fun QuestionCardPreview2Proposition() {
    LearnBasqueTheme {
        VocabularyQuestionCard(config2Prop)
    }
}

@DevicePreviews
@Composable
fun QuestionCardPreview4Propositions() {
    LearnBasqueTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PurpleGrey80
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                VocabularyQuestionCard(config4PropWithCorrectAnswer)
            }
        }
    }
}

private val config2Prop = VocabularyQuestionCardConfiguration(
    wordToTranslate = "Milesker",
    propositions = listOf(
        VocabularyQuestionCardConfiguration.Proposition("Merci", BlueGray),
        VocabularyQuestionCardConfiguration.Proposition("De rien", BlueGray),
    )
)
private val config4PropWithCorrectAnswer = VocabularyQuestionCardConfiguration(
    wordToTranslate = "Milesker",
    propositions = listOf(
        VocabularyQuestionCardConfiguration.Proposition("De rien", NeutralGray),
        VocabularyQuestionCardConfiguration.Proposition("Bonjour fezoij zefoijefz oijefz oijfez ioezfoij fezoij fezoij efz", NeutralGray),
        VocabularyQuestionCardConfiguration.Proposition("Merci", CorrectGreen),
        VocabularyQuestionCardConfiguration.Proposition("A bientot", IncorrectRed),
    )
)
private val config4PropWithWrongAnswer = VocabularyQuestionCardConfiguration(
    wordToTranslate = "Milesker",
    propositions = listOf(
        VocabularyQuestionCardConfiguration.Proposition("De rien", IncorrectRed),
        VocabularyQuestionCardConfiguration.Proposition("Bonjour", NeutralGray),
        VocabularyQuestionCardConfiguration.Proposition("Merci", CorrectGreen),
        VocabularyQuestionCardConfiguration.Proposition("A bientot", NeutralGray),
    )
)
