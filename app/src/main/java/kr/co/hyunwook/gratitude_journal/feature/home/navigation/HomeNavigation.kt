package kr.co.hyunwook.gratitude_journal.feature.home.navigation

import kotlinx.serialization.Serializable
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.home.HomeScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeNavGraph(
    paddingValues: PaddingValues,
    todayGratitudeSummary: TodayGratitudeSummary?
) {
    composable<Home> {
        HomeScreen(
            paddingValues = paddingValues,
            todayGratitudeSummary = todayGratitudeSummary
        )

    }
}

@Serializable
data object Home: Route {
    override val route = "kr.co.hyunwook.gratitude_journal.feature.home.navigation.Home"
}