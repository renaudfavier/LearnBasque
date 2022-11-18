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
import androidx.compose.ui.tooling.preview.Preview
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
fun NewWordCard(
    basque: String,
    french: String,
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

    }
}

@Preview
@Composable
fun NewWordCard() {
    LearnBasqueTheme {
        NewWordCard("Milesker", "Merci")
    }
}
