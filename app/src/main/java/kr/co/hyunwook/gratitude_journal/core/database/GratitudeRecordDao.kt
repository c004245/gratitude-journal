package kr.co.hyunwook.gratitude_journal.core.database

import kotlinx.coroutines.flow.Flow
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GratitudeRecordDao {

    //오늘 유저가 작성한 감사일기 가져오기
    @Query("SELECT gratitudeMemo FROM GratitudeRecord WHERE DATE(timeStamp / 1000, 'unixepoch') = Date('now')")
    fun getTodayGratitudeMemo(): Flow<String?>

    //오늘 작성 여부 확인
    @Query("SELECT COUNT(*) > 0 FROM GratitudeRecord WHERE DATE(timeStamp / 1000, 'unixepoch') = DATE('now')")
    fun hasWrittenToday(): Flow<Boolean>

    //연속 작성 일 수 가지고 오기
    @Query("""
        SELECT COUNT(*) FROM (
            WITH RECURSIVE DateStreak(date) AS (
                SELECT DATE('now')
                UNION ALL
                SELECT DATE(date, '-1 day')
                FROM DateStreak
                WHERE DATE(date, '-1 day') IN (SELECT DATE(timestamp / 1000, 'unixepoch') FROM GratitudeRecord)
            )
            SELECT * FROM DateStreak
        )
    """)
    fun getConsecutiveDays(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGratitudeRecord(recordDao: GratitudeRecord)
}


data class TodayGratitudeSummary(
    val hasWrittenToday: Boolean,
    val consecutiveDays: Int,
    val todayGratitudeMemo: String?
)