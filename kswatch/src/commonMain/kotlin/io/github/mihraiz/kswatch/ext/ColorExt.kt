package io.github.mihraiz.kswatch.ext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.github.mihraiz.kswatch.data.Hsv

/**
 * Returns an integer array for all color channels value.
 */
fun Color.argb(): Array<Int> {
    val argb = toArgb()
    val alpha = argb shr 24 and 0xff
    val red = argb shr 16 and 0xff
    val green = argb shr 8 and 0xff
    val blue = argb and 0xff
    return arrayOf(alpha, red, green, blue)
}

/**
 * Returns the red value as an integer.
 */
fun Color.red(): Int {
    return toArgb() shr 16 and 0xff
}

/**
 * Returns the green value as an integer.
 */
fun Color.green(): Int {
    return toArgb() shr 8 and 0xff
}

/**
 * Returns the blue value as an integer.
 */
fun Color.blue(): Int {
    return toArgb() and 0xff
}

/**
 * Returns the alpha value as an integer.
 */
fun Color.alpha(): Int {
    return toArgb() shr 24 and 0xff
}

/**
 * Returns ARGB color as a hex string.
 * @param hexPrefix Add # char before the hex number.
 * @param includeAlpha Include the alpha value within the hex string.
 */
fun Color.toHex(hexPrefix: Boolean = false, includeAlpha: Boolean = true): String {
    val (alpha, red, green, blue) = argb()
    return buildString {
        if (hexPrefix) {
            append("#")
        }
        if (includeAlpha) {
            append(alpha.toHex())
        }
        append(red.toHex())
        append(green.toHex())
        append(blue.toHex())
    }
}

/**
 * Returns the color in the HSV model, see [Hsv].
 */
fun Color.toHsv(): Hsv {
    val r = red
    val g = green
    val b = blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    val h = when {
        delta == 0f -> 0f
        max == r -> ((g - b) / delta) % 6
        max == g -> ((b - r) / delta) + 2
        else -> ((r - g) / delta) + 4
    } * 60

    return Hsv(
        hue = if (h < 0) h + 360 else h,
        saturation = if (max == 0f) 0f else delta / max,
        value = max,
    )
}

private fun Int.toHex(): String {
    return this.toString(16).let {
        if (it.length == 1) {
            "0$it"
        } else {
            it
        }
    }
}