package io.github.mihraiz.kswatch

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default styles for the pickers. Every factory parameter has a default derived
 * from [MaterialTheme], so an unstyled picker follows the app's theme.
 */
object ColorPickerDefaults {

    @Composable
    fun slideBarStyle(
        thumbColor: Color = Color.White,
        thumbRadius: Dp = 6.dp,
        height: Dp = 22.dp,
        shape: Shape = RoundedCornerShape(100),
        borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
        borderWidth: Dp = 1.dp,
    ): SlideBarStyle = SlideBarStyle(
        thumbColor = thumbColor,
        thumbRadius = thumbRadius,
        height = height,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
    )

    @Composable
    fun swatchRowStyle(
        size: Dp = 32.dp,
        shape: Shape = CircleShape,
        spacing: Dp = 2.dp,
        borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
        borderWidth: Dp = 1.dp,
        labelTextStyle: TextStyle = MaterialTheme.typography.labelMedium,
    ): SwatchRowStyle = SwatchRowStyle(
        size = size,
        shape = shape,
        spacing = spacing,
        borderColor = borderColor,
        borderWidth = borderWidth,
        labelTextStyle = labelTextStyle,
    )

    @Composable
    fun hexTextStyle(): TextStyle =
        MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)

    @Composable
    fun classicStyle(
        pickerSize: Dp = 200.dp,
        pickerShape: Shape = RoundedCornerShape(8.dp),
        selectorColor: Color = Color.White,
        slideBar: SlideBarStyle = slideBarStyle(),
        swatchRow: SwatchRowStyle = swatchRowStyle(),
        hexTextStyle: TextStyle = hexTextStyle(),
    ): ClassicColorPickerStyle = ClassicColorPickerStyle(
        pickerSize = pickerSize,
        pickerShape = pickerShape,
        selectorColor = selectorColor,
        slideBar = slideBar,
        swatchRow = swatchRow,
        hexTextStyle = hexTextStyle,
    )

    @Composable
    fun circleStyle(
        pickerSize: Dp = 200.dp,
        selectorColor: Color = Color.White,
        slideBar: SlideBarStyle = slideBarStyle(),
        swatchRow: SwatchRowStyle = swatchRowStyle(),
        hexTextStyle: TextStyle = hexTextStyle(),
    ): CircleColorPickerStyle = CircleColorPickerStyle(
        pickerSize = pickerSize,
        selectorColor = selectorColor,
        slideBar = slideBar,
        swatchRow = swatchRow,
        hexTextStyle = hexTextStyle,
    )

    @Composable
    fun ringStyle(
        pickerSize: Dp = 200.dp,
        ringWidth: Dp = 10.dp,
        previewRadius: Dp = 80.dp,
        selectorColor: Color = Color.White,
        slideBar: SlideBarStyle = slideBarStyle(),
        swatchRow: SwatchRowStyle = swatchRowStyle(),
        hexTextStyle: TextStyle = hexTextStyle(),
    ): RingColorPickerStyle = RingColorPickerStyle(
        pickerSize = pickerSize,
        ringWidth = ringWidth,
        previewRadius = previewRadius,
        selectorColor = selectorColor,
        slideBar = slideBar,
        swatchRow = swatchRow,
        hexTextStyle = hexTextStyle,
    )

    @Composable
    fun simpleRingStyle(
        pickerSize: Dp = 280.dp,
        colorWidth: Dp = 20.dp,
        swatchRow: SwatchRowStyle = swatchRowStyle(),
        hexTextStyle: TextStyle = hexTextStyle(),
    ): SimpleRingColorPickerStyle = SimpleRingColorPickerStyle(
        pickerSize = pickerSize,
        colorWidth = colorWidth,
        swatchRow = swatchRow,
        hexTextStyle = hexTextStyle,
    )
}
