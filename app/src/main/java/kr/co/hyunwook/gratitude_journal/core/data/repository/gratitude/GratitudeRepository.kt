package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary

interface GratitudeRepository {
    suspend fun saveShowOnBoarding()

    suspend fun getIsShowOnBoarding(): Flow<Boolean>

    suspend fun getTodayGratitudeSummary(): Flow<TodayGratitudeSummary>
}
