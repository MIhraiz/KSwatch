package io.github.mihraiz.kswatch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Closed set of picker styles. Each picker takes exactly its own style type, which
 * carries only the attributes that picker actually renders. Create instances with
 * the [ColorPickerDefaults] factories and adjust with `copy`.
 */
sealed interface ColorPickerStyle {
    /** Styling for the palette and recent-colors rows. */
    val swatchRow: SwatchRowStyle

    /** Text style of the hex readout in the preview row. */
    val hexTextStyle: TextStyle
}

/**
 * Style of the classic picker: a square saturation/value area with slide bars.
 *
 * @param pickerSize Size of the square selection area.
 * @param pickerShape Shape the selection area is clipped to.
 * @param selectorColor Ring color of the draggable selector.
 * @param slideBar Styling for the hue and alpha bars.
 */
data class ClassicColorPickerStyle(
    val pickerSize: Dp,
    val pickerShape: Shape,
    val selectorColor: Color,
    val slideBar: SlideBarStyle,
    override val swatchRow: SwatchRowStyle,
    override val hexTextStyle: TextStyle,
) : ColorPickerStyle

/**
 * Style of the circle picker: a hue/saturation wheel with slide bars.
 *
 * @param pickerSize Diameter of the wheel.
 * @param selectorColor Ring color of the draggable selector.
 * @param slideBar Styling for the brightness and alpha bars.
 */
data class CircleColorPickerStyle(
    val pickerSize: Dp,
    val selectorColor: Color,
    val slideBar: SlideBarStyle,
    override val swatchRow: SwatchRowStyle,
    override val hexTextStyle: TextStyle,
) : ColorPickerStyle

/**
 * Style of the ring picker: a hue ring with a center preview and slide bars.
 *
 * @param pickerSize Outer diameter of the ring area.
 * @param ringWidth Stroke width of the hue ring.
 * @param previewRadius Radius of the center color preview circle.
 * @param selectorColor Ring color of the draggable selector.
 * @param slideBar Styling for the lightness, darkness and alpha bars.
 */
data class RingColorPickerStyle(
    val pickerSize: Dp,
    val ringWidth: Dp,
    val previewRadius: Dp,
    val selectorColor: Color,
    val slideBar: SlideBarStyle,
    override val swatchRow: SwatchRowStyle,
    override val hexTextStyle: TextStyle,
) : ColorPickerStyle

/**
 * Style of the simple ring picker: concentric hue tracks split into sectors.
 * It has no slide bars and no round selector, so no such attributes exist here.
 *
 * @param pickerSize Size of the picker canvas.
 * @param colorWidth Stroke width of a single color track.
 */
data class SimpleRingColorPickerStyle(
    val pickerSize: Dp,
    val colorWidth: Dp,
    override val swatchRow: SwatchRowStyle,
    override val hexTextStyle: TextStyle,
) : ColorPickerStyle
