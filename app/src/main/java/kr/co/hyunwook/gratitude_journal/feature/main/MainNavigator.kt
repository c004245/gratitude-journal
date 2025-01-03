package kr.co.hyunwook.gratitude_journal.feature.main

import kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.Splash
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Splash


    fun navigateToOnBoarding(
        navOptions: NavOptions
    ) {
        navController.navigate(OnBoarding, navOptions = navOptions)
    }

    fun navigateToHome(
        navOptions: NavOptions
    ) {
        navController.navigate(Home, navOptions = navOptions)
    }


}


@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}