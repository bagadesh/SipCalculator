package com.bagadesh.sipcalculator.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bagadesh.sipcalculator.debug.customDebugValue

/**
 * Created by bagadesh on 09/08/22.
 */
@Composable
fun BlurController(
    radius: Dp = 196.dp,
    edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangle,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.zIndex(20f).blurControl(radiusX = radius, radiusY = radius, edgeTreatment = BlurredEdgeTreatment(OurShape))) {
        content()
    }
}

fun Modifier.blurControl(
    radiusX: Dp,
    radiusY: Dp,
    edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangle,
): Modifier {
    val clip: Boolean
    val tileMode: TileMode
    if (edgeTreatment.shape != null) {
        clip = true
        tileMode = TileMode.Clamp
    } else {
        clip = false
        tileMode = TileMode.Decal
    }
    return if ((radiusX > 0.dp && radiusY > 0.dp) || clip) {
        ourGraphicsLayer {
            val horizontalBlurPixels = radiusX.toPx()
            val verticalBlurPixels = radiusY.toPx()
            this.renderEffect =
                    // Only non-zero blur radii are valid BlurEffect parameters
                if (horizontalBlurPixels > 0f && verticalBlurPixels > 0f) {
                    BlurEffect(horizontalBlurPixels, verticalBlurPixels, tileMode)
                } else {
                    null
                }
            this.shape = edgeTreatment.shape ?: RectangleShape
            this.clip = true
            this.translationX = 0f
        }

    } else {
        this
    }
}

fun Modifier.ourGraphicsLayer(block: GraphicsLayerScope.() -> Unit): Modifier =
    this.then(
        OurBlockGraphicsLayerModifier(
            layerBlock = block,
            inspectorInfo = debugInspectorInfo {
                name = "graphicsLayer"
                properties["block"] = block
            }
        )
    )

private class OurBlockGraphicsLayerModifier(
    private val layerBlock: GraphicsLayerScope.() -> Unit,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        customDebugValue {
            appendTag("OurBlockGraphicsLayerModifier")
            appendLine("placeable ${placeable.width}")
            appendLine("placeable ${placeable.height}")
            appendLine("placeable ${placeable.measuredWidth}")
        }
        return layout(placeable.width,placeable.height) {
            placeable.placeWithLayer(0, 0, layerBlock = layerBlock, zIndex = -20f)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is OurBlockGraphicsLayerModifier) return false
        return layerBlock == other.layerBlock
    }

    override fun hashCode(): Int {
        return layerBlock.hashCode()
    }

    override fun toString(): String =
        "OurBlockGraphicsLayerModifier(" +
                "block=$layerBlock)"
}
