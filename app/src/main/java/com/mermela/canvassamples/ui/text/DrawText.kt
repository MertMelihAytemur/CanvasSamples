package com.mermela.canvassamples.ui.text

import android.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun CanvasText(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "Hello Canvas!",
                100f,
                100f,
                android.graphics.Paint().apply {
                    textSize = 100f
                    color = Color.RED
                    isAntiAlias = true
                }
            )
        }
    }
}