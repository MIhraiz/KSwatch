package io.github.mihraiz.kswatch.ext

import kotlin.math.roundToInt

/**
 * Moves a color channel toward 255 (white) by the given amount.
 */
internal fun Int.lighten(lightness: Float): Int {
    val newValue = (this + (255 - this) * lightness).coerceIn(0f, 255f)
    return newValue.roundToInt()
}

/**
 * Scales a color channel toward 0 (black) by the given amount.
 */
internal fun Int.darken(darkness: Float): Int {
    return (this - this * darkness).roundToInt()
}
