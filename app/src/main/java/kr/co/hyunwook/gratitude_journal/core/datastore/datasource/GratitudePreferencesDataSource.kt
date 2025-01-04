package kr.co.hyunwook.gratitude_journal.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface GratitudePreferencesDataSource {
    val isShowOnBoarding: Flow<Boolean>

    suspend fun getIsShowOnBoarding(isShow: Boolean)
}
