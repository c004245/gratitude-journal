package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import javax.inject.Inject

/**
 * 선택된 연도 데이터 가져오기
 */
class GetYearTotalGratitudeUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend operator fun invoke(year: String): Flow<List<Int>> = gratitudeRepository.getYearTotalGratitude(year)
}
