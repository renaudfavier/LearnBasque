package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.designsystem.theme.PurpleGrey80
import com.renaudfavier.learnbasque.core.designsystem.theme.Typography

@Composable
fun VocabularyScreen(
    modifier: Modifier = Modifier,
    viewModel: VocabularyViewModel
) {
    val vocabularyUiState = viewModel.vocabularyUiState.collectAsState()

    Surface(
        modifier = modifier,
        color = PurpleGrey80
    ) {
        Box(modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
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
    }
}

@Composable
fun EasyMemoryCard(
    model: VocabularyUiState.EasyMemoryCardUiModel,
    onProp1Click: () -> Unit,
    onProp2Click: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = model.backgroundColor ?: Color.White),
    ) {
        Text(
            text = model.wordToTranslate,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
            style = Typography.displayMedium,
        )
        // TODO : LOOK AT FLOW ROW
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onProp1Click) {
                Text(text = model.proposition1)
            }
            Button(onClick = onProp2Click,
            modifier = Modifier.padding(start = 12.dp)) {
                Text(text = model.proposition2)
            }
        }
    }
}

@Preview
@Composable
fun MemoryCardPreview() {
    LearnBasqueTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PurpleGrey80
        ) {
            EasyMemoryCard(
                model = fakeCardModel,
                onProp1Click = {},
                onProp2Click = {},
            )
        }
    }
}


private val fakeCardModel = VocabularyUiState.EasyMemoryCardUiModel(
    wordId = "",
    wordToTranslate = "Milesker Anitz",
    proposition1 = "Peut-être",
    proposition2 = "Merci beaucoup",
)
