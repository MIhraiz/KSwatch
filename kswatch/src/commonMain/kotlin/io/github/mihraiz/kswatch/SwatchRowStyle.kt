package io.github.mihraiz.kswatch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Appearance of the palette and recent-colors swatch rows.
 *
 * @param size Size of a single swatch.
 * @param shape Shape of a single swatch.
 * @param spacing Space between swatches.
 * @param borderColor Color of a swatch's outline.
 * @param borderWidth Width of a swatch's outline.
 * @param labelTextStyle Text style of the section label above the row.
 */
data class SwatchRowStyle(
    val size: Dp,
    val shape: Shape,
    val spacing: Dp,
    val borderColor: Color,
    val borderWidth: Dp,
    val labelTextStyle: TextStyle,
)
