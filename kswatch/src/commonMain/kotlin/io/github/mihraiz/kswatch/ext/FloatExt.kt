package io.github.mihraiz.kswatch.ext

/**
 * Moves a color channel toward 255 (white) by the given amount.
 */
internal fun Float.lighten(lightness: Float): Float {
    return this + (255 - this) * lightness
}

/**
 * Scales a color channel toward 0 (black) by the given amount.
 */
internal fun Float.darken(darkness: Float): Float {
    return this - this * darkness
}
