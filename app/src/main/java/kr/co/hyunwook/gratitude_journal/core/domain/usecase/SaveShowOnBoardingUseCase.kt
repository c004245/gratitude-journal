package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import javax.inject.Inject

class SaveShowOnBoardingUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend operator fun invoke() = gratitudeRepository.saveShowOnBoarding()
}
