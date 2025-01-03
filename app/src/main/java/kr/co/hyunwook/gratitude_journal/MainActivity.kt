package kr.co.hyunwook.gratitude_journal

import dagger.hilt.android.AndroidEntryPoint
import kr.co.hyunwook.gratitude_journal.feature.main.MainNavigator
import kr.co.hyunwook.gratitude_journal.feature.main.MainScreen
import kr.co.hyunwook.gratitude_journal.feature.main.rememberMainNavigator
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }


        setContent {
            val navigator: MainNavigator = rememberMainNavigator()

            GratitudeTheme {
                CompositionLocalProvider {
                    MainScreen(navigator = navigator)
                }

            }
        }
    }
}
