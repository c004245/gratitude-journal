package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord

interface GratitudeRepository {
    suspend fun saveShowOnBoarding()

    suspend fun getIsShowOnBoarding(): Flow<Boolean>

    suspend fun getTodayGratitudeSummary(): Flow<TodayGratitudeSummary>

    suspend fun saveGratitudeRecord(gratitudeRecord: GratitudeRecord)

    suspend fun getYearTotalGratitude(year: String): Flow<List<Int>>
}
