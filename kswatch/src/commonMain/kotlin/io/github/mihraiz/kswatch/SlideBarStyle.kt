package io.github.mihraiz.kswatch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Appearance of the slide bars shared by the classic, circle and ring pickers.
 *
 * @param thumbColor Color of the draggable thumb.
 * @param thumbRadius Radius of the draggable thumb.
 * @param height Height of the bar.
 * @param shape Shape the bar is clipped to.
 * @param borderColor Color of the bar's outline.
 * @param borderWidth Width of the bar's outline.
 */
data class SlideBarStyle(
    val thumbColor: Color,
    val thumbRadius: Dp,
    val height: Dp,
    val shape: Shape,
    val borderColor: Color,
    val borderWidth: Dp,
)
