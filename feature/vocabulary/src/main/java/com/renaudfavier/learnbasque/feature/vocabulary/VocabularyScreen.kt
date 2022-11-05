package com.renaudfavier.learnbasque.feature.vocabulary

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme

@Composable
fun VocabularyScreen(
    modifier: Modifier = Modifier,
) {
    Text(text = "hello")
}

@Preview
@Composable
fun VocabularyScreenLoading() {
    LearnBasqueTheme {
        VocabularyScreen(
        )
    }
}
