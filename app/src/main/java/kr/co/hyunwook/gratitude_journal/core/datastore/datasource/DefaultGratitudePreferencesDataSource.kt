package kr.co.hyunwook.gratitude_journal.core.datastore.datasource

import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit

class DefaultGratitudePreferencesDataSource @Inject constructor(
    @Named("gratitude") private val dataStore: DataStore<Preferences>
): GratitudePreferencesDataSource {

    object PreferencesKey {
        val IS_SHOW_ONBOARDING = booleanPreferencesKey("IS_SHOW_ONBOARDING")
    }

    override val isShowOnBoarding = dataStore.data.map { preferences ->
        preferences[PreferencesKey.IS_SHOW_ONBOARDING] ?: false
    }

    override suspend fun saveShowOnBoarding() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.IS_SHOW_ONBOARDING] = true
        }
    }

}
