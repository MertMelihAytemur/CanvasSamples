package com.mermela.canvassamples.ui.touch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Simple aim lab style game where user has to click on the moving ball within the time limit to score points
 */
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var points by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize().navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreText(points = points)
            ActionButton(
                isTimerRunning = isTimerRunning,
                onClick = {
                    points = 0
                    isTimerRunning = !isTimerRunning
                })
            CountDownTimer(
                isTimerRunning = isTimerRunning
            ) {
                isTimerRunning = false
            }
        }
        BallClicker(
            enabled = isTimerRunning,
            onBallClick = {
                points += 1
            }
        )
    }
}

@Composable
private fun ScoreText(modifier: Modifier = Modifier, points: Int) {
    Text(
        modifier = modifier,
        text = "Points: $points",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    isTimerRunning: Boolean,
    onClick: () -> Unit
) {
    val text = if (isTimerRunning) "Reset" else "Start"
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
private fun CountDownTimer(
    modifier: Modifier = Modifier,
    time: Int = 30000,
    isTimerRunning: Boolean = false,
    onTimerFinish: () -> Unit
) {
    var currentTime by remember { mutableIntStateOf(time) }
    LaunchedEffect(key1 = isTimerRunning, key2 = currentTime) {
        if (!isTimerRunning) {
            currentTime = time
            return@LaunchedEffect
        }
        if (currentTime > 0) {
            delay(1000)
            currentTime -= 1000
        } else {
            onTimerFinish.invoke()
        }
    }
    Text(
        modifier = modifier,
        text = (currentTime / 1000).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun BallClicker(
    modifier: Modifier = Modifier,
    radius: Float = 100f,
    enabled: Boolean = false,
    ballColor: Color = Color.Red,
    onBallClick: () -> Unit
) {
    //If click point distance from center of the ball is less than radius, then it's a hit otherwise miss
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        var randomPosition by remember {
            mutableStateOf(
                randomOffsetGenerator(
                    width = constraints.maxWidth,
                    height = constraints.maxHeight,
                    radius = radius
                )
            )
        }
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(enabled) {
                    if (!enabled) return@pointerInput
                    detectTapGestures(
                        onTap = { offset ->
                            val distance = kotlin.math.sqrt(
                                (offset.x - randomPosition.x) * (offset.x - randomPosition.x) +
                                        (offset.y - randomPosition.y) * (offset.y - randomPosition.y)
                            )
                            if (distance <= radius) {
                                randomPosition = randomOffsetGenerator(
                                    width = constraints.maxWidth,
                                    height = constraints.maxHeight,
                                    radius = radius
                                )
                                onBallClick.invoke()
                            }
                        }
                    )
                }
        ) {
            drawCircle(
                color = ballColor,
                radius = radius,
                center = randomPosition
            )
        }
    }
}

private fun randomOffsetGenerator(width: Int, height: Int, radius: Float): Offset {
    return Offset(
        x = Random.nextInt(radius.roundToInt(), width - radius.roundToInt()).toFloat(),
        y = Random.nextInt(radius.roundToInt(), height - radius.roundToInt()).toFloat()
    )
}