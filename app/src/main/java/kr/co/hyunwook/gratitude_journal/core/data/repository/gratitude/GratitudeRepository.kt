package kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude

import kotlinx.coroutines.flow.Flow

interface GratitudeRepository {
    suspend fun saveShowOnBoarding()

    suspend fun getIsShowOnBoarding(): Flow<Boolean>
}
