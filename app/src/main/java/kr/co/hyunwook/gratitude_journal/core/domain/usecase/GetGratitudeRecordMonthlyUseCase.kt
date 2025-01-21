package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordMonthly
import javax.inject.Inject

/**
 * 해당 연월에 있는 감사 일기 가져오기
 */
class GetGratitudeRecordMonthlyUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend operator fun invoke(yearMonth: String): Flow<List<GratitudeRecordMonthly>> =
        gratitudeRepository.getGratitudeRecordsByMonth(yearMonth)

}
