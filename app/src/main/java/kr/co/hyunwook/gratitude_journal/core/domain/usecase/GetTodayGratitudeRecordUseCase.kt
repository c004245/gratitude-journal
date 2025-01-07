package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import javax.inject.Inject

class GetTodayGratitudeRecordUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend operator fun invoke(): Flow<TodayGratitudeSummary> =
        gratitudeRepository.getTodayGratitudeSummary()
}
