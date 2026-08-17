package io.github.mihraiz.kswatch.data

/**
 * A color in the HSV model.
 *
 * @param hue Position on the color wheel in degrees, 0..360.
 * @param saturation Purity of the color, 0 (gray) .. 1 (vivid).
 * @param value Brightness, 0 (black) .. 1 (full).
 */
data class Hsv(
    val hue: Float,
    val saturation: Float,
    val value: Float,
)
