package com.renaudfavier.learnbasque.feature.microlearning

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.renaudfavier.learnbasque.core.designsystem.theme.PurpleGrey80
import com.renaudfavier.learnbasque.core.ui.LinesBackground
import com.renaudfavier.learnbasque.core.ui.NewWordCard
import com.renaudfavier.learnbasque.core.ui.VocabularyQuestionCard

@Composable
fun MicroLearningScreen(
    modifier: Modifier = Modifier,
    viewModel: MicroLearningViewModel
) {

    val microLearningUiState = viewModel.microLearningUiState.collectAsState()
    val backgroundUiState = viewModel.backgroundUiState.collectAsState()

    Surface(
        modifier = modifier,
        color = PurpleGrey80
    ) {
        Box(modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            LinesBackground(
                lines = backgroundUiState.value,
                modifier = modifier
            )
            when(val state = microLearningUiState.value) {
                MicroLearningUiState.Loading -> {
                    CircularProgressIndicator()
                }
                MicroLearningUiState.Error -> {
                    Text(text = "error")
                }
                is MicroLearningUiState.Content.NewWord -> {
                    NewWordCard(basque = state.basque, french = state.french)
                }
                is MicroLearningUiState.Content.Translation -> {
                    VocabularyQuestionCard(model = state.cardConfig)
                }
            }
        }
    }
}
