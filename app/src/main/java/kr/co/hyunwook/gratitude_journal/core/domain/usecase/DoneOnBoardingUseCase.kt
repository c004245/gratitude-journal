package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import javax.inject.Inject

/**
 * 온보딩 봤는지 여부
 */
class DoneOnBoardingUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend

}
