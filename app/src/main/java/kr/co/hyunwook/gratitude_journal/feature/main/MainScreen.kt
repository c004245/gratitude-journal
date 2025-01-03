package kr.co.hyunwook.gratitude_journal.feature.main

import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.homeNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.onboardingNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.splashNavGraph
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val coroutineScope= rememberCoroutineScope()

    LaunchedEffect(Unit) {

    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { ExitTransition.None }
                ) {
                    splashNavGraph(
                        navigateToOnBoarding = {
                            navigate(navigator, OnBoarding)
                        },
                        navigateToHome = {
                            navigate(navigator, Home)
                        }
                    )

                    onboardingNavGraph(
                        navigateToHome = {
                            navigate(navigator, Home)
                        }
                    )
                    homeNavGraph(
                        paddingValues = paddingValues
                    )
                }
            }
        }
    )
}


fun navigate(navigator: MainNavigator, route: Route) {
    val navOptions = navOptions {
        popUpTo(navigator.navController.graph.findStartDestination().id) {
            inclusive = true
        }
        launchSingleTop = true
    }

    when (route) {
        OnBoarding -> {
            navigator.navigateToOnBoarding(navOptions)
        }
        Home -> {
            navigator.navigateToHome(navOptions)
        }

    }
}