package kr.co.hyunwook.gratitude_journal.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GratitudeRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gratitudeMemo: String,
    val timeStamp: Long,
    val gratitudeType: GratitudeType
)


enum class GratitudeType {
    MY,
    PARENT
}