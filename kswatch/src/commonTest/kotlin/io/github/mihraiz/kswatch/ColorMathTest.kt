package io.github.mihraiz.kswatch

import androidx.compose.ui.graphics.Color
import io.github.mihraiz.kswatch.ext.blue
import io.github.mihraiz.kswatch.ext.toHsv
import io.github.mihraiz.kswatch.ext.green
import io.github.mihraiz.kswatch.ext.red
import io.github.mihraiz.kswatch.ext.toHex
import io.github.mihraiz.kswatch.helper.ColorPickerHelper
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ColorMathTest {

    private val samples = listOf(
        Color(0xffff0000), Color(0xff00ff00), Color(0xff0000ff),
        Color(0xffffff00), Color(0xff00ffff), Color(0xffff00ff),
        Color(0xff8b4513), Color(0xff2e8b57), Color(0xff9370db),
        Color(0xffc71585), Color(0xff20b2aa), Color(0xff778899),
        Color(0x80e1a1c1), Color(0xff123456), Color(0xfffedcba),
    )

    private fun assertChannelsClose(expected: Color, actual: Color, tolerance: Int = 2) {
        assertTrue(
            abs(expected.red() - actual.red()) <= tolerance &&
                abs(expected.green() - actual.green()) <= tolerance &&
                abs(expected.blue() - actual.blue()) <= tolerance,
            "expected #${expected.toHex()} but was #${actual.toHex()}"
        )
    }

    @Test
    fun hsvToColorInvertsColorToHsv() {
        for (color in samples) {
            val hsv = color.toHsv()
            val restored = ColorPickerHelper.hsvToColor(hsv.hue, hsv.saturation, hsv.value, color.alpha)
            assertChannelsClose(color, restored)
            assertTrue(abs(color.alpha - restored.alpha) <= 2f / 255f)
        }
    }

    @Test
    fun hueToRgbMatchesHsvHue() {
        for (i in 0..99) {
            val progress = i / 100.0
            val (r, g, b) = ColorPickerHelper.hueToRgb(progress)
            val hue = Color(r, g, b).toHsv().hue
            val expected = progress * 360.0
            val diff = abs(hue - expected).let { minOf(it, 360.0 - it) }
            assertTrue(diff <= 1.5, "progress $progress gave hue $hue")
        }
    }

    @Test
    fun classicSeedRoundTrips() {
        for (color in samples) {
            val hsv = color.toHsv()
            val (r, g, b) = ColorPickerHelper.hueToRgb(hsv.hue / 360.0)
            val rangeColor = Color(r, g, b)
            val xProgress = 1f - hsv.saturation
            val yProgress = 1f - hsv.value
            val restored = ColorPickerHelper.classicColorAt(rangeColor, xProgress, yProgress, color.alpha)
            assertChannelsClose(color, restored)
        }
    }

    @Test
    fun toHexProducesKnownValues() {
        assertEquals("ffff0000", Color(0xffff0000).toHex())
        assertEquals("#ff00ff00", Color(0xff00ff00).toHex(hexPrefix = true))
        assertEquals("0000ff", Color(0xff0000ff).toHex(includeAlpha = false))
    }
}
