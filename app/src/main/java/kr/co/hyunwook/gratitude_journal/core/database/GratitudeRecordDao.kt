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
    @Query("SELECT gratitudeMemo || ' ' || gratitudeType FROM GratitudeRecord WHERE DATE(timeStamp / 1000, 'unixepoch') = DATE('now')")
    fun getTodayGratitudeMemo(): Flow<String?>


    //오늘 작성 여부 확인
    @Query("SELECT COUNT(*) > 0 FROM GratitudeRecord WHERE DATE(timeStamp / 1000, 'unixepoch') = DATE('now')")
    fun hasWrittenToday(): Flow<Boolean>


    //몇 일 연속 작성하고 있는가
    @Query("""
    SELECT 
        CASE WHEN EXISTS (
            SELECT 1 FROM GratitudeRecord
        ) THEN 
            (SELECT COUNT(*) FROM (
                WITH RECURSIVE DateStreak(date) AS (
                    SELECT DATE('now')
                    UNION ALL
                    SELECT DATE(date, '-1 day')
                    FROM DateStreak
                    WHERE DATE(date, '-1 day') IN (SELECT DATE(timestamp / 1000, 'unixepoch') FROM GratitudeRecord)
                )
                SELECT * FROM DateStreak
            ))
        ELSE 0
    END AS consecutiveDays
""")
    fun getConsecutiveDays(): Flow<Int>



    //감사 일기 저장
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGratitudeRecord(recordDao: GratitudeRecord)

    //해당 연도에 데이터가 있는 달 리스트 출력
    @Query("""
        SELECT strftime('%m', datetime(timeStamp / 1000, 'unixepoch')) AS month 
        FROM GratitudeRecord 
        WHERE strftime('%Y', datetime(timeStamp / 1000, 'unixepoch')) = :year
        GROUP BY month
        ORDER BY month ASC
    """)
    fun getYearTotalGratitude(year: String): Flow<List<Int>>


    //해당 연도월에 감사일기 가져오기
    @Query("""
        SELECT gratitudeMemo, gratitudeType 
        FROM GratitudeRecord 
        WHERE strftime('%Y-%m', datetime(timeStamp / 1000, 'unixepoch')) = :yearMonth
    """)
    fun getGratitudeRecordsByMonth(yearMonth: String): Flow<List<GratitudeRecordMonthly>>
}


data class GratitudeRecordMonthly(
    val gratitudeMemo: String,
    val gratitudeType: String
)

data class TodayGratitudeSummary(
    val hasWrittenToday: Boolean,
    val consecutiveDays: Int,
    val todayGratitudeMemo: String?
)