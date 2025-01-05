package kr.co.hyunwook.gratitude_journal.feature.main.splash

import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.Gray900
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purpleC4
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.Splash
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit = {},
    navigateToOnBoarding: () -> Unit = {},
    viewModel: SplashViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.showSplash()
    }

    LaunchedEffect(viewModel.sideEffects, lifecycleOwner) {
        viewModel.sideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SplashSideEffect.NavigateToOnBoarding -> navigateToOnBoarding()
                    is SplashSideEffect.NavigateToHome -> navigateToHome()
                }
            }
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true,
            transformColorForLightContent = { Gray900 }
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true,
            navigationBarContrastEnforced = false

        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(purpleC4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "splash_icon"
        )
    }
}
