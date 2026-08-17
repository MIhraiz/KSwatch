package io.github.mihraiz.kswatch.ext

/**
 * Moves a color channel toward 255 (white) by the given amount.
 */
internal fun Double.lighten(lightness: Float): Double {
    return (this + (255 - this) * lightness).coerceIn(0.0, 255.0)
}

/**
 * Scales a color channel toward 0 (black) by the given amount.
 */
internal fun Double.darken(darkness: Float): Double {
    return this - this * darkness
}
