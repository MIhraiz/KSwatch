package io.github.mihraiz.kswatch.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import io.github.mihraiz.kswatch.data.ColorRange
import io.github.mihraiz.kswatch.ext.blue
import io.github.mihraiz.kswatch.ext.toHsv
import io.github.mihraiz.kswatch.ext.darken
import io.github.mihraiz.kswatch.ext.green
import io.github.mihraiz.kswatch.ext.lighten
import io.github.mihraiz.kswatch.ext.red
import kotlin.math.roundToInt


internal object ColorPickerHelper {
    fun calculateRangeProgress(progress: Double): Pair<Double, ColorRange> {
        val range: ColorRange
        return progress * 6 - when {
            progress < 1f / 6 -> {
                range = ColorRange.RedToYellow
                0
            }

            progress < 2f / 6 -> {
                range = ColorRange.YellowToGreen
                1
            }

            progress < 3f / 6 -> {
                range = ColorRange.GreenToCyan
                2
            }

            progress < 4f / 6 -> {
                range = ColorRange.CyanToBlue
                3
            }

            progress < 5f / 6 -> {
                range = ColorRange.BlueToPurple
                4
            }

            else -> {
                range = ColorRange.PurpleToRed
                5
            }
        } to range
    }

    /**
     * Maps a hue progress in 0..1 to the channels of the pure hue color.
     */
    fun hueToRgb(progress: Double): Triple<Int, Int, Int> {
        val (rangeProgress, range) = calculateRangeProgress(progress.coerceIn(0.0, 1.0))
        return when (range) {
            ColorRange.RedToYellow -> Triple(255, (255 * rangeProgress).roundToInt(), 0)
            ColorRange.YellowToGreen -> Triple((255 * (1 - rangeProgress)).roundToInt(), 255, 0)
            ColorRange.GreenToCyan -> Triple(0, 255, (255 * rangeProgress).roundToInt())
            ColorRange.CyanToBlue -> Triple(0, (255 * (1 - rangeProgress)).roundToInt(), 255)
            ColorRange.BlueToPurple -> Triple((255 * rangeProgress).roundToInt(), 0, 255)
            ColorRange.PurpleToRed -> Triple(255, 0, (255 * (1 - rangeProgress)).roundToInt())
        }
    }

    fun hsvToColor(hue: Float, saturation: Float, value: Float, alpha: Float = 1f): Color {
        val (r, g, b) = hueToRgb(hue / 360.0)
        return Color(
            r.lighten(1f - saturation).darken(1f - value),
            g.lighten(1f - saturation).darken(1f - value),
            b.lighten(1f - saturation).darken(1f - value),
            (255 * alpha).roundToInt()
        )
    }

    /**
     * The classic picker's forward formula: the color at the given picker progress values,
     * where xProgress is the lighten amount (left edge = 1) and yProgress the darken
     * amount (top edge = 0).
     */
    fun classicColorAt(rangeColor: Color, xProgress: Float, yProgress: Float, alpha: Float): Color {
        return Color(
            rangeColor.red().lighten(xProgress).darken(yProgress),
            rangeColor.green().lighten(xProgress).darken(yProgress),
            rangeColor.blue().lighten(xProgress).darken(yProgress),
            (255 * alpha).roundToInt()
        )
    }

    /**
     * Inverse of [classicColorAt] for the picker area. The max channel is scaled by V and
     * the min channel is raised by the lighten amount, so x = width * S and y = height * (1 - V).
     */
    fun calculateInitialPickerLocation(initialColor: Color, size: IntSize): Offset {
        val hsv = initialColor.toHsv()
        return Offset(
            x = size.width * hsv.saturation,
            y = size.height * (1f - hsv.value)
        )
    }
}
