package kr.co.hyunwook.gratitude_journal.feature.main

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.black21
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purple6C
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purpleC4
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.homeNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.onboardingNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.splashNavGraph
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import android.graphics.drawable.Icon
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val coroutineScope = rememberCoroutineScope()

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
        },
        bottomBar = {
            if (navigator.isShowBottomBar()) {
                CustomBottomBar(
                    onClickAddGratitude = {

                    },
                    onClickHome = {

                    },
                    onClickTotal = {

                    }
                )

            }
        }
    )
}

@Composable
fun CustomBottomBar(
    onClickAddGratitude: () -> Unit,
    onClickHome: () -> Unit,
    onClickTotal: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .navigationBarsPadding()
            .height(80.dp)
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth().height(56.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        onClickHome()
                    }.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_dailygrow_select_tab
                        ),
                        tint = purpleC4,
                        contentDescription = "Home"
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = GratitudeTheme.typography.regular,
                        fontSize = 14.sp,
                        color = black24

                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            onClickTotal()
                        }
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_dailygrow_select_tab
                        ),
                        tint = purple6C, // 상태에 따른 색상 변경
                        contentDescription = "Collect",
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 14.sp,
                        style = GratitudeTheme.typography.regular,
                        color = black21
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_add_tab),
            contentDescription = "Center Button",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -28.dp)
                .size(80.dp)
                .clickable { onClickAddGratitude() }
        )
    }

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