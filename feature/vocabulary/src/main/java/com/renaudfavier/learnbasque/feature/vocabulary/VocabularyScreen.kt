package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme

@Composable
fun VocabularyScreen(
    modifier: Modifier = Modifier,
    viewModel: VocabularyViewModel
) {
    val vocabularyUiState = viewModel.vocabularyUiState.collectAsState()

    when(val value = vocabularyUiState.value) {
        VocabularyUiState.Loading -> {
            CircularProgressIndicator()
        }
        VocabularyUiState.Error -> {
            Text(text = "error")
        }
        is VocabularyUiState.EasyMemoryCardUiModel -> {
            EasyMemoryCard(
                model = value,
                onProp1Click = viewModel::answerProposition1,
                onProp2Click = viewModel::answerProposition2,
            )
        }
    }
}

@Composable
fun EasyMemoryCard(
    model: VocabularyUiState.EasyMemoryCardUiModel,
    onProp1Click: () -> Unit,
    onProp2Click: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = model.backgroundColor ?: Color.White)
    ) {
        Text(text = model.wordToTranslate)
        Button(onClick = onProp1Click) {
            Text(text = model.proposition1)
        }
        Button(onClick = onProp2Click) {
            Text(text = model.proposition2)
        }
    }
}

@Preview
@Composable
fun MemoryCardPreview() {
    LearnBasqueTheme {
        EasyMemoryCard(
            model = fakeCardModel,
            onProp1Click = {},
            onProp2Click = {},
        )
    }
}

private val fakeCardModel = VocabularyUiState.EasyMemoryCardUiModel(
    id = "",
    wordToTranslate = "motBasque",
    proposition2 = "prop1",
    proposition1 = "prop1"
)
