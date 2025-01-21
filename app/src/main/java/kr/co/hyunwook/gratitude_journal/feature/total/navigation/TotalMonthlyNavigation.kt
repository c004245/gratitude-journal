package kr.co.hyunwook.gratitude_journal.feature.total.navigation

import kotlinx.serialization.Serializable
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.total.TotalMonthlyScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.totalMonthlyNavGraph(
    selectMonthly: Int
) {
    composable<TotalMonthly> {
        TotalMonthlyScreen(
            selectMonth = selectMonthly
        )
    }
}


@Serializable
data object TotalMonthly: Route {
    override val route = "kr.co.hyunwook.gratitude_journal.feature.total.navigation.TotalMonthly"
}
