package com.mermela.canvassamples.ui.weightpicker.model

sealed class LineType {
    object Normal : LineType()
    object FiveStep : LineType()
    object TenStep : LineType()
}