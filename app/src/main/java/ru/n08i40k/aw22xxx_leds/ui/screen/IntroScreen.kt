package ru.n08i40k.aw22xxx_leds.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.n08i40k.aw22xxx_leds.R
import ru.n08i40k.aw22xxx_leds.ui.theme.AppTheme

enum class HelloScreenStep {
    Enter,
    Main,
    Exit,
}

@Preview(
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun HelloScreenPreview() {
    AppTheme {
        Surface(Modifier.fillMaxSize()) {
            IntroScreen {}
        }
    }
}

@Composable
fun IntroScreen(onComplete: () -> Unit) {
    var state by remember { mutableStateOf(HelloScreenStep.Enter) }

    Box(Modifier.fillMaxSize(), Alignment.Center) {
        when (state) {
            HelloScreenStep.Enter,
            HelloScreenStep.Exit,
            HelloScreenStep.Main -> {
                LaunchedEffect(state) {
                    if (state != HelloScreenStep.Enter)
                        return@LaunchedEffect

                    delay(500)
                    state = HelloScreenStep.Main
                }

                HelloMainStep(
                    state,
                    onNext = { state = HelloScreenStep.Exit },
                    onComplete = onComplete
                )
            }
        }
    }
}

private const val DEFAULT_SHOW_DURATION = 400
private const val DEFAULT_HIDE_DURATION = 250

@Composable
fun HelloMainStep(step: HelloScreenStep, onNext: () -> Unit, onComplete: () -> Unit) {
    var showCard by remember { mutableStateOf(false) }
    var expandRow by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    var showButton by remember { mutableStateOf(false) }
    var enableButton by remember { mutableStateOf(false) }

    LaunchedEffect(step) {
        when (step) {
            HelloScreenStep.Main -> {
                delay(500)

                showCard = true
                delay(DEFAULT_SHOW_DURATION.toLong())

                expandRow = true
                delay(DEFAULT_SHOW_DURATION.toLong() + 750)

                showText = true
                delay(DEFAULT_SHOW_DURATION.toLong())
                delay(100)

                showButton = true
                delay(DEFAULT_SHOW_DURATION.toLong())

                enableButton = true
            }

            HelloScreenStep.Exit -> {
                enableButton = false
                showButton = false
                delay(100)

                showText = false
                delay(DEFAULT_HIDE_DURATION.toLong() * 2 + 350)

                showCard = false
                delay(DEFAULT_HIDE_DURATION.toLong())

                onComplete()
            }

            else -> {}
        }
    }

    val cardOpacity by animateFloatAsState(
        if (showCard) 1f else 0f,
        tween(if (showCard) DEFAULT_SHOW_DURATION else DEFAULT_HIDE_DURATION)
    )

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            Modifier
                .graphicsLayer { alpha = cardOpacity }
                .clip(CardDefaults.shape)
                .background(
                    Brush.horizontalGradient(
                        0.0f to Color(0xFF84D8D1),
                        0.3f to Color(0xFF7BB892),
                        1.0f to Color(0xFFEDDD53),
                    )
                ),
//            colors = CardDefaults.cardColors(Color(132, 216, 209), Color.DarkGray),
            colors = CardDefaults.cardColors(Color.Transparent),
            border = BorderStroke(0.5f.dp, MaterialTheme.colorScheme.inverseSurface)
        ) {
            Row {
                val targetSpacing = with(LocalDensity.current) {
                    MaterialTheme.typography.displayMedium.fontSize.toDp()
                } / 4

                AnimatedVisibility(
                    expandRow,
                    enter = expandHorizontally(
                        tween(DEFAULT_SHOW_DURATION)
                        { OvershootInterpolator().getInterpolation(it) },
                        Alignment.Start
                    ),
                    exit = shrinkHorizontally(
                        tween(DEFAULT_HIDE_DURATION)
                        { OvershootInterpolator().getInterpolation(it) },
                        Alignment.Start
                    )
                ) {
                    Text(
                        stringResource(R.string.app_name_0),
                        Modifier.padding(
                            targetSpacing,
                            targetSpacing,
                            targetSpacing / 2,
                            targetSpacing
                        ),
                        color = Color(0xFFEDDD53),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Card(colors = CardDefaults.cardColors(Color.Transparent)) {
                    Text(
                        stringResource(R.string.app_name_1),
                        Modifier.padding(
                            targetSpacing / 2,
                            targetSpacing,
                            targetSpacing,
                            targetSpacing
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AnimatedVisibility(
            showText,
            enter = expandVertically(tween(DEFAULT_SHOW_DURATION))
                    + fadeIn(tween(DEFAULT_SHOW_DURATION, DEFAULT_SHOW_DURATION)),
            exit = fadeOut(tween(DEFAULT_HIDE_DURATION))
                    + shrinkVertically(tween(DEFAULT_HIDE_DURATION, DEFAULT_HIDE_DURATION)),
        ) {
            Column(
                Modifier.padding(40.dp, 60.dp),
                Arrangement.spacedBy(10.dp),
                Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    stringResource(R.string.welcome_text),
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            showButton,
            enter = slideInVertically(tween(DEFAULT_SHOW_DURATION))
                    + fadeIn(tween(DEFAULT_SHOW_DURATION)),
            exit = slideOutVertically(tween(DEFAULT_HIDE_DURATION))
                    + fadeOut(tween(DEFAULT_HIDE_DURATION))
        ) {
            Button(
                { if (enableButton) onNext() },
                Modifier.fillMaxWidth()
            ) { Text(stringResource(R.string.continue_button)) }
        }
    }
}
