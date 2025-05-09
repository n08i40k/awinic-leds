package ru.n08i40k.aw22xxx_leds.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.itemsIndexed
import ru.n08i40k.aw22xxx_leds.R
import ru.n08i40k.aw22xxx_leds.bridge.LedFrq
import ru.n08i40k.aw22xxx_leds.ui.model.LedsViewModel
import ru.n08i40k.aw22xxx_leds.ui.widgets.CategoryChip
import ru.n08i40k.aw22xxx_leds.ui.widgets.TitledColorPickerDialog
import ru.n08i40k.aw22xxx_leds.ui.widgets.TitledDropdownMenuMap
import ru.n08i40k.aw22xxx_leds.ui.widgets.TitledSwitcher
import ru.n08i40k.aw22xxx_leds.ui.widgets.TitledVerticalPagerDialog

@Composable
fun RawSetScreen(viewModel: LedsViewModel = viewModel()) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(Modifier.padding(20.dp, 0.dp)) {
        val modifier = Modifier.padding(0.dp, 10.dp)

        TitledSwitcher(
            uiState.enabled,
            { viewModel.setEnabled(it) },
            title = stringResource(R.string.led_hwen_title),
            description = "/sys/class/leds/aw22xxx_led/hwen",
            modifier = modifier
        )

        val effects = uiState.availableEffect

        TitledDropdownMenuMap(
            uiState.currentEffect,
            { viewModel.setEffect(effects[it].index) },
            values = effects,
            valueKey = { it.name },
            title = stringResource(R.string.led_effect_title),
            description = "/sys/class/leds/aw22xxx_led/effect",
            modifier = modifier
        )

        CategoryChip(stringResource(R.string.cat_application), modifier)

        TitledSwitcher(
            uiState.useSavedSettings,
            { viewModel.setUseSavedSettings(it) },
            title = stringResource(R.string.led_use_saved_settings_title),
            description = stringResource(R.string.led_use_saved_settings_description),
            modifier = modifier
        )

        TitledSwitcher(
            uiState.useOwnValues,
            { viewModel.setUseOwnValues(it) },
            title = stringResource(R.string.led_use_own_values_title),
            description = stringResource(R.string.led_use_own_values_description),
            modifier = modifier
        )

        CategoryChip(stringResource(R.string.cat_own_values), modifier)

        TitledVerticalPagerDialog(
            LedFrq.values.indexOf(uiState.frequency),
            { viewModel.setFrequency(LedFrq.values[it]) },
            values = LedFrq.values,
            valueKey = { "$it Hz" },
            title = stringResource(R.string.led_frq_title),
            description = "/sys/class/leds/aw22xxx_led/frq",
            modifier = modifier
        )

        LazyColumn {
            itemsIndexed(uiState.colors) { index, color ->
                TitledColorPickerDialog(
                    color,
                    { viewModel.setColor(index.toUByte(), it) },
                    stringResource(R.string.led_rgb_title, index),
                    description = "/sys/class/leds/aw22xxx_led/rgb:$index",
                    modifier = modifier
                )
            }
        }
    }
}
