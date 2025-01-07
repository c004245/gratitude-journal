package kr.co.hyunwook.gratitude_journal.core.database

import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GratitudeRecord::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gratitudeRecordDao(): GratitudeRecordDao
}
