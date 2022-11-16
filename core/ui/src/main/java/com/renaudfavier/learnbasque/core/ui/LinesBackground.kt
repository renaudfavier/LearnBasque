package com.renaudfavier.learnbasque.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.renaudfavier.learnbasque.core.designsystem.theme.LearnBasqueTheme
import com.renaudfavier.learnbasque.core.designsystem.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun LinesBackground(
    lines: List<Line>,
    modifier: Modifier
) {
    Canvas(modifier = modifier) {
        lines.forEach {
            drawLine(
                color = it.color,
                start = it.start * size.maxDimension,
                end = it.end * size.maxDimension,
                strokeWidth = 5f
            )
        }
    }
}

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.White
)

class OrigamiBackgroundAnimator (
    private val uiScope: CoroutineScope,
    vesselNumber: Int = 32,
    private val color: Color = Purple40,
    private val maxDistance: Float = 0.20f,
): CoroutineScope by uiScope {

    private var myLineFlow: MutableStateFlow<List<Line>> = MutableStateFlow(emptyList())
    val lineFLow: StateFlow<List<Line>> = myLineFlow

    private val vessels = Array(vesselNumber) { Vessel() }
    private val vesselPairs: List<Pair<Vessel, Vessel>>

    init {
        val mutableList = mutableListOf<Pair<Vessel, Vessel>>()
        for(i in 0 until vesselNumber) {
            for (j in (i + 1) until vesselNumber) {
                mutableList.add(Pair(vessels[i], vessels[j]))
            }
        }
        vesselPairs = mutableList
    }

    fun start() = launch {
        while (true) {
            vessels.forEach { it.progress(1f) }
            myLineFlow.value = vesselPairs
                .map { Pair(it, it.first.distanceFrom(it.second)) }
                .filter { it.second < maxDistance }
                .map { (vessels, distance) ->
                    Line(vessels.first.offset, vessels.second.offset, distance.toColor())
                }
            delay(40)
        }
    }

    private fun Float.toColor() = color.copy(alpha = (maxDistance - this) / maxDistance)

    private fun Vessel.distanceFrom(other: Vessel): Float {
        val xDiff = this.offset.x - other.offset.x
        val yDiff = this.offset.y - other.offset.y
        return sqrt(xDiff*xDiff + yDiff*yDiff)
    }

    class Vessel(
        baseSpeed: Float = 0.010f,
    ) {
        var offset: Offset = Offset(Random.nextFloat(), Random.nextFloat())
        private val directionRandomness: Double = Random.nextFloat() * 2 * Math.PI
        private val speedRandomness: Float = max(0.5f, Random.nextFloat())

        private val xOffsetDiff = baseSpeed * speedRandomness * sin(directionRandomness).toFloat()
        private val yOffsetDiff = baseSpeed * speedRandomness * cos(directionRandomness).toFloat()

        fun progress(speedMultiplier: Float) {
            var x = offset.x + xOffsetDiff * speedMultiplier
            var y = offset.y + yOffsetDiff * speedMultiplier
            if (x > 1) x = 0f else if (x < 0) x = 1f
            if (y > 1) y = 0f else if (y < 0) y = 1f
            offset = Offset(x, y)
        }
    }
}

@Preview
@Composable
fun CellBackgroundPreview() {
    LearnBasqueTheme {
        LinesBackground(
            lines = listOf(
                Line(
                    Offset(0.2f, 0.2f),
                    Offset(0.5f, 0.5f)
                )
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}
