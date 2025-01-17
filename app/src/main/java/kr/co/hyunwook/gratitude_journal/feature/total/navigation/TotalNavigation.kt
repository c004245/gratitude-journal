package kr.co.hyunwook.gratitude_journal.feature.total.navigation

import kotlinx.serialization.Serializable
import kr.co.hyunwook.gratitude_journal.core.navigation.Route
import kr.co.hyunwook.gratitude_journal.feature.total.TotalScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

fun NavGraphBuilder.totalNavGraph() {
    composable<Total> {
        TotalScreen()
    }
}

@Serializable
data object Total: Route {
    override val route = "kr.co.hyunwook.gratitude_journal.feature.total.navigation.Total"
}