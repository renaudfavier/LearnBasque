package com.renaudfavier.learnbasque.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.designsystem.theme.Typography

@Composable
fun NewWordCard(
    basque: String,
    french: String,
    onOkClick: ()-> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = "Nouveau mot !",
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ),
            style = Typography.displayLarge,
        )
        Text(
            text = basque,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ),
            style = Typography.displayMedium,
        )
        Text(
            text = french,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ),
            style = Typography.displayMedium,
        )
        Button(
            onClick = onOkClick,
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ).align(Alignment.End),
        ) {
            Text(text = "OK")
        }
    }
}

@Preview
@Composable
fun NewWordCardPreview() {
    LearnBasqueTheme {
        NewWordCard("Milesker", "Merci", {})
    }
}
