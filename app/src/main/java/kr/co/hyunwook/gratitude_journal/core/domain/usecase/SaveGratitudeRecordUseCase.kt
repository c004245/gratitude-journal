package kr.co.hyunwook.gratitude_journal.core.domain.usecase

import kr.co.hyunwook.gratitude_journal.core.data.repository.gratitude.GratitudeRepository
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import javax.inject.Inject

class SaveGratitudeRecordUseCase @Inject constructor(
    private val gratitudeRepository: GratitudeRepository
) {
    suspend operator fun invoke(gratitudeRecord: GratitudeRecord) =
        gratitudeRepository.saveGratitudeRecord(gratitudeRecord)
}
