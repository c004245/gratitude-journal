package kr.co.hyunwook.gratitude_journal.feature.main

import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.yellow50
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.add.BottomSheetContent
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home
import kr.co.hyunwook.gratitude_journal.feature.home.navigation.homeNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.OnBoarding
import kr.co.hyunwook.gratitude_journal.feature.main.onboarding.navigation.onboardingNavGraph
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.splashNavGraph
import kr.co.hyunwook.gratitude_journal.feature.total.navigation.Total
import kr.co.hyunwook.gratitude_journal.feature.total.navigation.TotalMonthly
import kr.co.hyunwook.gratitude_journal.feature.total.navigation.totalMonthlyNavGraph
import kr.co.hyunwook.gratitude_journal.feature.total.navigation.totalNavGraph
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isSheetOpen by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableStateOf(SelectedTab.HOME) }

    var selectYearMonth by remember { mutableStateOf("") }
    val currentRoute =
        navigator.navController.currentBackStackEntryFlow.collectAsState(null).value?.destination?.route

    val todayGratitudeSummary = viewModel.todayGratitudeSummary.collectAsState().value


    LaunchedEffect(currentRoute) {
        if (currentRoute == Home.route) {
            viewModel.getTodayGratitudeRecord()
        }
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
                        todayGratitudeSummary = todayGratitudeSummary
                    )
                    totalNavGraph(
                        navigateToTotalMonthly = {
                            selectYearMonth = it
                            navigate(navigator, TotalMonthly)
                        }
                    )
                    totalMonthlyNavGraph(
                        selectYearMonth,
                        navigateToTotal = {
                            navigate(navigator, Total)
                        })

                }
            }
        },
        bottomBar = {
            if (navigator.isShowBottomBar()) {
                CustomBottomBar(
                    todayGratitudeSummary = todayGratitudeSummary,
                    onClickAddGratitude = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                        isSheetOpen = true
                    },
                    onClickHome = {
                        selectedTab = SelectedTab.HOME
                        navigate(navigator, Home)
                    },
                    onClickTotal = {
                        selectedTab = SelectedTab.TOTAL
                        navigate(navigator, Total)
                    },
                    selectedTab = selectedTab
                )

            }
        }
    )
    if (isSheetOpen) {
        ModalBottomSheet(
            containerColor = black24,
            onDismissRequest = {
                isSheetOpen = false
            },
            sheetState = sheetState
        ) {
            BottomSheetContent(
                onClose = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    isSheetOpen = false
                }
            )

        }
    }
}

@Composable
fun CustomBottomBar(
    todayGratitudeSummary: TodayGratitudeSummary?,
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
            onClickAddGratitude = onClickAddGratitude,
            todayGratitudeSummary = todayGratitudeSummary
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
                iconRes = R.drawable.ic_today_select_tab,
                labelRes = R.string.text_today_gratitude,
                modifier = Modifier.weight(1f)
            )
            BottomBarItem(
                onClick = onClickTotal,
                isSelected = selectedTab == SelectedTab.TOTAL,
                iconRes = R.drawable.ic_total_select_tab,
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
            .height(2.dp)
            .drawBehind {
                val gapWidth = 90.dp.toPx() // 중앙 비어있는 공간의 너비
                val lineHeight = size.height // 라인의 높이 (6.dp)
                val lineWidth = (size.width - gapWidth) / 2 // 각각의 라인 너비

                val path = Path().apply {
                    // 왼쪽 라인
                    moveTo(0f, 0f) // 시작점 (왼쪽 위)
                    lineTo(lineWidth, 0f) // 상단 직선
                    lineTo(lineWidth, lineHeight) // 오른쪽 하단 직선
                    lineTo(0f, lineHeight) // 왼쪽 하단 직선
                    close() // 경로 닫기

                    // 오른쪽 라인
                    moveTo(size.width - lineWidth, 0f) // 오른쪽 상단 시작점
                    lineTo(size.width, 0f) // 오른쪽 상단 직선
                    lineTo(size.width, lineHeight) // 오른쪽 하단 직선
                    lineTo(size.width - lineWidth, lineHeight) // 왼쪽 하단 직선
                    close() // 경로 닫기
                }

                drawPath(path, color = yellow50)
            }
    )
}


@Composable
private fun BottomAddGratitudeButton(
    modifier: Modifier = Modifier,
    onClickAddGratitude: () -> Unit,
    todayGratitudeSummary: TodayGratitudeSummary?
) {
    val interactionSource = remember { MutableInteractionSource() } // remember로 메모이제이션

    Image(
        painter = if (todayGratitudeSummary?.hasWrittenToday == true) {
            painterResource(id = R.drawable.ic_today_done_gratitude)
        } else {
            painterResource(id = R.drawable.ic_today_not_gratitude)
        },
        contentDescription = "Center Button",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .offset(y = -30.dp)
            .size(75.dp)
            .clip(CircleShape) // 원 모양으로 클릭 영역을 제한
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true, radius = 40.dp) // 클릭 효과를 원형으로 설정
            ) {
                if (todayGratitudeSummary?.hasWrittenToday == false) {
                    onClickAddGratitude()
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

        Total -> {
            navigator.navigateToTotal(navOptions)
        }

        TotalMonthly -> {
            navigator.navigateToTotalMonthly(navOptions)
        }

    }
}

enum class SelectedTab {
    HOME, TOTAL
}