package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordDao
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.datastore.datasource.GratitudePreferencesDataSource
import kr.co.hyunwook.gratitude_journal.util.zip
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
        return zip(
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
    override suspend fun saveGratitudeRecord(gratitudeRecord: GratitudeRecord) {
        gratitudeRecordDao.saveGratitudeRecord(gratitudeRecord)
    }
}
