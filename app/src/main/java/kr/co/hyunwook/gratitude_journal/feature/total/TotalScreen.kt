package kr.co.hyunwook.gratitude_journal.feature.total

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalScreen(
    viewModel: TotalViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    )
}