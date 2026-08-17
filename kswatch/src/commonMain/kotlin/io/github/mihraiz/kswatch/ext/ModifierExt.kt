package io.github.mihraiz.kswatch.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind

/**
 * Draw behind the composable a transparent effect background.
 * @param verticalBoxesAmount Amount of the white and gray boxes for a single column.
 */
fun Modifier.transparentBackground(verticalBoxesAmount: Int = 10) = this.drawBehind {
    drawTransparentBackground(verticalBoxesAmount)
}
