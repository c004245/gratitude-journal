package kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation

import kotlinx.serialization.Serializable
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.OnBoardingScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.onboardingNavGraph(
    navigateToHome: () -> Unit
) {
    composable<OnBoarding> {
        OnBoardingScreen(
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object OnBoarding: Route {
    override val route = "kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding"
}