package io.github.mihraiz.kswatch.pickers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.mihraiz.kswatch.data.ColorRange
import io.github.mihraiz.kswatch.data.Colors.gradientColors
import io.github.mihraiz.kswatch.ext.blue
import io.github.mihraiz.kswatch.ext.toHsv
import io.github.mihraiz.kswatch.ext.darken
import io.github.mihraiz.kswatch.ext.drawColorSelector
import io.github.mihraiz.kswatch.ext.green
import io.github.mihraiz.kswatch.ext.lighten
import io.github.mihraiz.kswatch.ext.red
import io.github.mihraiz.kswatch.helper.BoundedPointStrategy
import io.github.mihraiz.kswatch.helper.ColorPickerHelper
import io.github.mihraiz.kswatch.helper.MathHelper
import io.github.mihraiz.kswatch.helper.MathHelper.getBoundedPointWithInRadius
import io.github.mihraiz.kswatch.helper.MathHelper.getLength
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@ExperimentalComposeUiApi
@Composable
internal fun RingColorPicker(
    modifier: Modifier = Modifier,
    ringWidth: Dp,
    previewRadius: Dp,
    showLightColorBar: Boolean,
    showDarkColorBar: Boolean,
    showAlphaBar: Boolean,
    showColorPreview: Boolean,
    initialColor: Color = Color.Red,
    onPickedColor: (Color) -> Unit,
) {
    val density = LocalDensity.current
    val ringWidthPx = remember {
        with(density) { ringWidth.toPx() }
    }
    val previewRadiusPx = remember {
        with(density) { previewRadius.toPx() }
    }
    var radius by remember {
        mutableStateOf(0f)
    }
    var pickerLocation by remember(radius) {
        mutableStateOf(
            getBoundedPointWithInRadius(
                radius * 2,
                radius,
                getLength(radius * 2, radius, radius),
                radius - ringWidthPx / 2,
                BoundedPointStrategy.Edge
            )
        )
    }
    var selectedColor by remember {
        mutableStateOf(initialColor)
    }
    var color by remember {
        mutableStateOf(initialColor)
    }
    var lightColor by remember {
        mutableStateOf(initialColor)
    }
    var darkColor by remember {
        mutableStateOf(initialColor)
    }
    var lightness by remember {
        mutableStateOf(0f)
    }
    var darkness by remember {
        mutableStateOf(0f)
    }
    var alpha by remember {
        mutableStateOf(initialColor.alpha)
    }

    LaunchedEffect(initialColor, radius) {
        if (radius > 0) {
            val hsv = initialColor.toHsv()
            val angle = MathHelper.toRadians(hsv.hue.toDouble())
            val saturation = hsv.saturation

            val x = radius + cos(angle) * saturation * radius
            val y = radius + sin(angle) * saturation * radius
            pickerLocation = Offset(x.toFloat(), y.toFloat())
        }
    }
    LaunchedEffect(selectedColor, lightness, darkness, alpha) {
        var red = selectedColor.red().lighten(lightness)
        var green = selectedColor.green().lighten(lightness)
        var blue = selectedColor.blue().lighten(lightness)
        lightColor = Color(red, green, blue, 255)
        red = red.darken(darkness)
        green = green.darken(darkness)
        blue = blue.darken(darkness)
        darkColor = Color(red, green, blue, 255)
        color = Color(red, green, blue, (255 * alpha).roundToInt())
        onPickedColor(color)
    }
    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Canvas(modifier = modifier
            .size(200.dp)
            .onSizeChanged {
                radius = it.width.toFloat() / 2
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x
                    val y = change.position.y

                    val angle = ((atan2(y - radius, x - radius) * 180.0 / PI) + 360) % 360
                    val length = getLength(x, y, radius)
                    val progress = angle / 360f
                    val (rangeProgress, range) = ColorPickerHelper.calculateRangeProgress(
                        progress
                    )

                    val (red, green, blue) = when (range) {
                        ColorRange.RedToYellow -> Triple(
                            255,
                            (255 * rangeProgress).roundToInt(),
                            0
                        )

                        ColorRange.YellowToGreen -> Triple(
                            (255 * (1 - rangeProgress)).roundToInt(),
                            255,
                            0
                        )

                        ColorRange.GreenToCyan -> Triple(
                            0,
                            255,
                            (255 * rangeProgress).roundToInt()
                        )

                        ColorRange.CyanToBlue -> Triple(
                            0,
                            (255 * (1 - rangeProgress)).roundToInt(),
                            255
                        )

                        ColorRange.BlueToPurple -> Triple(
                            (255 * rangeProgress).roundToInt(),
                            0,
                            255
                        )

                        ColorRange.PurpleToRed -> Triple(
                            255,
                            0,
                            (255 * (1 - rangeProgress)).roundToInt()
                        )
                    }

                    pickerLocation = getBoundedPointWithInRadius(
                        x,
                        y,
                        length,
                        radius - ringWidthPx / 2,
                        BoundedPointStrategy.Edge
                    )
                    selectedColor = Color(red, green, blue)
                }
            }) {
            drawCircle(
                Brush.sweepGradient(gradientColors),
                radius = radius - ringWidthPx / 2f,
                style = Stroke(ringWidthPx)
            )
            if (showColorPreview) {
                drawCircle(
                    color,
                    radius = previewRadiusPx
                )
            }
            drawColorSelector(selectedColor, pickerLocation)
        }
        if (showLightColorBar) {
            Spacer(modifier = Modifier.height(16.dp))
            ColorSlideBar(
                colors = listOf(
                    Color.White,
                    selectedColor
                ),initialColor=initialColor
            ) {
                lightness = 1 - it
            }
        }
        if (showDarkColorBar) {
            Spacer(modifier = Modifier.height(16.dp))
            ColorSlideBar(colors = listOf(Color.Black, lightColor),initialColor=initialColor) {
                darkness = 1 - it
            }
        }
        if (showAlphaBar) {
            Spacer(modifier = Modifier.height(16.dp))
            ColorSlideBar(colors = listOf(Color.Transparent, darkColor),initialColor=initialColor) {
                alpha = it
            }
        }
    }
}

