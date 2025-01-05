package kr.co.hyunwook.gratitude_journal.feature.main.splash

import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.Gray900
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purpleC4
import kr.co.hyunwook.gratitude_journal.feature.main.splash.navigation.Splash
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            transformColorForLightContent = { black24 }
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true,
            navigationBarContrastEnforced = false

        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(black24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = "splash_icon"
            )
            Spacer(Modifier.height(25.dp))
            Text(
                text = stringResource(R.string.text_splash),
                style = GratitudeTheme.typography.regular,
                color = Color.White,
                fontSize = 20.sp
            )

        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter)
                .wrapContentWidth()
                .padding(PaddingValues(bottom = 24.dp)),
            text = stringResource(R.string.text_splash_bottom),
            color = yellowFF,
            fontSize = 12.sp
        )




    }
}
