package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordDao
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.datastore.datasource.GratitudePreferencesDataSource
import javax.inject.Inject

class GratitudeRepositoryImpl @Inject constructor(
    private val gratitudePreferencesDataSource: GratitudePreferencesDataSource,
    private val gratitudeRecordDao: GratitudeRecordDao
): GratitudeRepository {

    override suspend fun getIsShowOnBoarding(): Flow<Boolean> = gratitudePreferencesDataSource.isShowOnBoarding

    override suspend fun saveShowOnBoarding() {
        gratitudePreferencesDataSource.saveShowOnBoarding()
    }

    override suspend fun getTodayGratitudeSummary(): Flow<TodayGratitudeSummary> {
        return combine(
            gratitudeRecordDao.hasWrittenToday(),
            gratitudeRecordDao.getConsecutiveDays(),
            gratitudeRecordDao.getTodayGratitudeMemo()
        ) { hasWrittenToday, consecutiveDays, todayGratitudeMemo ->
            TodayGratitudeSummary(
                hasWrittenToday = hasWrittenToday,
                consecutiveDays = consecutiveDays,
                todayGratitudeMemo = todayGratitudeMemo
            )
        }


    }
}
