package com.bagadesh.sipcalculator.ui.custom

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.bagadesh.sipcalculator.debug.customDebugValue

val OurShape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val ourSize = size.copy(width = size.width /2 )
        val outline = Outline.Rectangle(ourSize.toRect())
        customDebugValue {
            appendTag("OurShape")
            appendLine("size = $size")
            appendLine("ourSize = $ourSize")
            appendLine("outline.rect = ${outline.rect}")
            appendLine("outline.bounds = ${outline.bounds}")
        }
        return outline
    }

    override fun toString(): String = "RectangleShape"
}