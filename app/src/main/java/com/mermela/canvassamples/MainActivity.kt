package com.mermela.canvassamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.mermela.canvassamples.ui.touch.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //MyCanvas()
            MainScreen()
        }
    }

    @Composable
    fun MyCanvas(modifier: Modifier = Modifier) {
        Canvas(
            modifier = Modifier
                .padding(20.dp)
                .size(300.dp)
        ) {
            drawRect(
                color = Color.Black,
                size = size
            )
            drawRect(
                color = Color.Red,
                topLeft = Offset(100f,100f),
                size = Size(100f,100f),
                style = Stroke(
                    width = 5f,
                )
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Red, Color.Green),
                    center = center,
                    radius = 100f
                ),
                radius = 100f
            )
            drawArc(
                color = Color.Green,
                startAngle = 0f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = Offset(100f, 500f),
                size = Size(200f, 200f),
                style = Stroke(
                    width = 5f
                )
            )
            drawOval(
                color = Color.Magenta,
                topLeft = Offset(550f, 100f),
                size = Size(200f, 300f)
            )

            drawLine(
                color = Color.Yellow,
                start = Offset(500f, 500f),
                end = Offset(700f, 700f),
                strokeWidth = 10f
            )
        }
    }
}