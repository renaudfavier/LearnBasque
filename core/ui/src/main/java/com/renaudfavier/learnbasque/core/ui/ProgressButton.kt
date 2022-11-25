package com.renaudfavier.learnbasque.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.renaudfavier.learnbasque.core.ui.R
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.designsystem.theme.Purple40
import com.renaudfavier.learnbasque.core.designsystem.theme.Purple80

@Composable
fun ProgressButton(
    config: ProgressButtonConfig
) = ProgressButton(
    onClick = config.onClick,
    modifier = config.modifier,
    colorIcon = config.colorIcon,
    colorProgress = config.colorProgress,
    progress = config.progress,
    alpha = config.alpha
)

data class ProgressButtonConfig(
    val onClick: () -> Unit,
    val modifier: Modifier = Modifier,
    val colorIcon: Color = Purple40,
    val colorProgress: Color = Purple80,
    val progress: Float = 0f,
    val alpha: Float = 1f,
)

@Composable
fun ProgressButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorIcon: Color = Purple40,
    colorProgress: Color = Purple80,
    progress: Float = 0f,
    alpha: Float = 1f,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .alpha(alpha)
            .border(BorderStroke(1.dp, color = colorIcon), CircleShape)
            ,
        contentPadding = PaddingValues(0.dp)
    ) {
        Surface {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                color = colorProgress,
                trackColor = colorProgress.copy(alpha = 0.8f),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24),
                contentDescription = "next",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                tint = colorIcon
            )
        }
   }
}

@Preview
@Composable
fun ProgressButtonPreview() {
    LearnBasqueTheme {
        ProgressButton(
            onClick = {},
            modifier = Modifier.size(50.dp),
            colorProgress = Purple80,
            progress = 0.43f,
        )
    }
}
