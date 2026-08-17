package io.github.mihraiz.kswatch

import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ColorPickerStateTest {

    @Test
    fun holdsAndUpdatesColor() {
        val state = ColorPickerState(Color(0xff2e8b57))
        assertEquals(Color(0xff2e8b57), state.color)
        state.color = Color(0xff123456)
        assertEquals(Color(0xff123456), state.color)
    }

    @Test
    fun saverRoundTripsIncludingAlpha() {
        val state = ColorPickerState(Color(0x802e8b57))
        val saved = with(ColorPickerState.Saver) { SaverScope { true }.save(state) }
        assertNotNull(saved)
        val restored = ColorPickerState.Saver.restore(saved)
        assertNotNull(restored)
        assertEquals(state.color.value, restored.color.value)
    }
}
