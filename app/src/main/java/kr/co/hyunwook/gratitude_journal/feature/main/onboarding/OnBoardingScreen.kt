package kr.co.hyunwook.gratitude_journal.feature.main.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnBoardingScreen(
    navigateToHome: () -> Unit = {},
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

    }

}
