package ru.n08i40k.aw22xxx_leds.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.n08i40k.aw22xxx_leds.proto.settings
import ru.n08i40k.aw22xxx_leds.ui.theme.AppTheme

private enum class AppInnerRoute(val routeId: String) {
    Intro("intro"),
    Main("main")
}

@Composable
private fun AppRouter() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val nav: (from: AppInnerRoute, to: AppInnerRoute) -> Unit =
        { from, to ->
            navController.navigate(to.routeId) {
                popUpTo(from.routeId) {
                    inclusive = true
                }
            }
        }

    val showIntro = runBlocking { !context.settings.data.map { it.completedIntro }.first() }

    NavHost(
        navController,
        if (showIntro)
            AppInnerRoute.Intro.routeId
        else
            AppInnerRoute.Main.routeId,
        Modifier.fillMaxSize()
    ) {
        composable(AppInnerRoute.Intro.routeId) {
            IntroScreen {
                runBlocking {
                    context.settings.updateData {
                        it.toBuilder().setCompletedIntro(true).build()
                    }
                }

                nav(AppInnerRoute.Intro, AppInnerRoute.Main)
            }
        }

        composable(AppInnerRoute.Main.routeId) { RawSetScreen() }
    }
}

@Composable
fun AppScreen() {
    AppTheme {
        Surface {
            Box(Modifier.windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Vertical))) {
                AppRouter()
            }
        }
    }
}