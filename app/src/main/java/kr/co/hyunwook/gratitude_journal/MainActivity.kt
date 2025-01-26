package kr.co.hyunwook.gratitude_journal

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kr.co.hyunwook.gratitude_journal.feature.main.MainNavigator
import kr.co.hyunwook.gratitude_journal.feature.main.MainScreen
import kr.co.hyunwook.gratitude_journal.feature.main.rememberMainNavigator
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }


        requestNotificationPermission()
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()

            GratitudeTheme {
                CompositionLocalProvider {
                    MainScreen(navigator = navigator)
                }

            }
        }
    }

    private fun requestNotificationPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Log.d("MainActivity", "Notification permission granted")
                    // 권한 허용 시 추가 로직 작성 가능
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Log.d("MainActivity", "Notification permission denied")
                    // 권한 거부 시 추가 로직 작성 가능
                }
            })
            .setPermissions(android.Manifest.permission.POST_NOTIFICATIONS)
            .check()
    }
}
