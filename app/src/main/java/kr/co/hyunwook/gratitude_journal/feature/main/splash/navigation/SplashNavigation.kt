package kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation

import kotlinx.serialization.Serializable
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.main.splash.SplashScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.splashNavGraph(
    navigateToOnBoarding: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable<Splash> {
        SplashScreen(
            navigateToHome = navigateToHome,
            navigateToOnBoarding = navigateToOnBoarding

        )
    }
}

@Serializable
data object Splash: Route {
    override val route = "kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.Splash"
}
