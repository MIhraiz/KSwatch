package io.github.mihraiz.kswatch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/**
 * Holds the current color of a picker and is its source of truth. Reading [color]
 * inside composition subscribes to changes; writing it from outside the picker
 * repositions the picker's thumbs and selector.
 */
class ColorPickerState(initialColor: Color) {

    var color: Color by mutableStateOf(initialColor)

    companion object {
        /**
         * Saves the raw color bits, so alpha and the color space survive restoration.
         */
        val Saver: Saver<ColorPickerState, Long> = Saver(
            save = { it.color.value.toLong() },
            restore = { ColorPickerState(Color(it.toULong())) },
        )
    }
}

/**
 * Remembers a [ColorPickerState] that survives configuration changes and
 * process recreation.
 */
@Composable
fun rememberColorPickerState(initialColor: Color = Color.Red): ColorPickerState =
    rememberSaveable(saver = ColorPickerState.Saver) { ColorPickerState(initialColor) }
