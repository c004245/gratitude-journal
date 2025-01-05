package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.datastore.datasource.GratitudePreferencesDataSource
import javax.inject.Inject

class GratitudeRepositoryImpl @Inject constructor(
    private val gratitudePreferencesDataSource: GratitudePreferencesDataSource
): GratitudeRepository {

    override suspend fun getIsShowOnBoarding(): Flow<Boolean> = gratitudePreferencesDataSource.isShowOnBoarding

    override suspend fun saveShowOnBoarding() {
        gratitudePreferencesDataSource.saveShowOnBoarding()
    }
}
