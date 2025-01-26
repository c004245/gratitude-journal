package kr.co.hyunwook.gratitude_journal

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import android.app.Application

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // Remote Config 초기화
        FirebaseApp.initializeApp(this)
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // Fetch interval (1시간)
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("deer_message" to "디어가 아직 감사를 보내지않았어요."))
    }
}
