package kr.co.hyunwook.gratitude_journal.feature.main

import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.yellow50
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.add.BottomSheetContent
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.homeNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.onboardingNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.splashNavGraph
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isSheetOpen by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableStateOf(SelectedTab.HOME) }

    LaunchedEffect(Unit) {
//        viewModel()

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
                        coroutineScope.launch {
                            sheetState.show()
                        }
                        isSheetOpen = true
                    },
                    onClickHome = {
                        selectedTab = SelectedTab.HOME
                    },
                    onClickTotal = {
                        selectedTab = SelectedTab.TOTAL

                    },
                    selectedTab = selectedTab
                )

            }
        }
    )
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                isSheetOpen = false
            },
            sheetState = sheetState
        ) {
            BottomSheetContent(
                onSaveGratitude = { gratitudeRecord ->

                }
            )
        }
    }
}

@Composable
fun CustomBottomBar(
    onClickAddGratitude: () -> Unit,
    onClickHome: () -> Unit,
    onClickTotal: () -> Unit,
    selectedTab: SelectedTab
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .navigationBarsPadding()
            .height(80.dp)
    ) {
        BottomBarSurface(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClickHome = onClickHome,
            onClickTotal = onClickTotal,
            selectedTab = selectedTab
        )

        TopDecorativeLine(
            modifier = Modifier.align(Alignment.TopCenter)
        )
        BottomAddGratitudeButton(
            modifier = Modifier.align(Alignment.TopCenter),
            onClickAddGratitude = onClickAddGratitude
        )
    }

}

@Composable
private fun BottomBarSurface(
    modifier: Modifier = Modifier,
    onClickHome: () -> Unit,
    onClickTotal: () -> Unit,
    selectedTab: SelectedTab
) {
    Surface(
        color = black24,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                onClick = onClickHome,
                isSelected = selectedTab == SelectedTab.HOME,
                iconRes = R.drawable.ic_dailygrow_select_tab,
                labelRes = R.string.text_today_gratitude,
                modifier = Modifier.weight(1f)
            )
            BottomBarItem(
                onClick = onClickTotal,
                isSelected = selectedTab == SelectedTab.TOTAL,
                iconRes = R.drawable.ic_dailygrow_select_tab,
                labelRes = R.string.text_total_gratitude,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean,
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(
                id = iconRes
            ),
            tint = if (isSelected) yellowFF else Color.White,
            contentDescription = "Home"
        )
        Text(
            text = stringResource(id = labelRes),
            style = GratitudeTheme.typography.regular,
            fontSize = 14.sp,
            color = if (isSelected) yellowFF else Color.White

        )
    }
}


    @Composable
    private fun TopDecorativeLine(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp)
                .drawBehind {
                    val gapWidth = 90.dp.toPx()
                    val lineHeight = size.height
                    val lineWidth = (size.width - gapWidth) / 2
                    val cornerRadius = lineHeight / 2

                    val path = Path().apply {
                        // 왼쪽 라인
                        moveTo(0f, lineHeight / 2)
                        arcTo(
                            rect = Rect(0f, 0f, lineHeight, lineHeight),
                            startAngleDegrees = 90f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(lineWidth, 0f)
                        lineTo(lineWidth, lineHeight)
                        arcTo(
                            rect = Rect(lineWidth - lineHeight, 0f, lineWidth, lineHeight),
                            startAngleDegrees = 0f,
                            sweepAngleDegrees = -180f,
                            forceMoveTo = false
                        )
                        close()

                        // 오른쪽 라인
                        moveTo(size.width - lineWidth, lineHeight / 2)
                        arcTo(
                            rect = Rect(size.width - lineHeight, 0f, size.width, lineHeight),
                            startAngleDegrees = 90f,
                            sweepAngleDegrees = 180f,
                            forceMoveTo = false
                        )
                        lineTo(size.width - lineWidth, 0f)
                        lineTo(size.width - lineWidth, lineHeight)
                        arcTo(
                            rect = Rect(size.width - lineWidth, 0f, size.width - lineWidth + lineHeight, lineHeight),
                            startAngleDegrees = 0f,
                            sweepAngleDegrees = -180f,
                            forceMoveTo = false
                        )
                        close()
                    }

                    drawPath(path, color = yellow50)
                }
        )
    }

@Composable
private fun BottomAddGratitudeButton(modifier: Modifier = Modifier, onClickAddGratitude: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_today_done_gratitude),
        contentDescription = "Center Button",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .offset(y = -30.dp)
            .size(75.dp)
            .clickable { onClickAddGratitude() }
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

enum class SelectedTab {
    HOME, TOTAL
}