package kr.co.hyunwook.gratitude_journal.feature.total

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetGratitudeRecordMonthlyUseCase
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetYearTotalGratitudeUseCase
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class TotalViewModel @Inject constructor(
    private val getYearTotalGratitudeUseCase: GetYearTotalGratitudeUseCase,
    private val getGratitudeRecordMonthlyUseCase: GetGratitudeRecordMonthlyUseCase
): ViewModel() {

    fun getYearGratitudeRecord(year: String) {
        viewModelScope.launch {
            getYearTotalGratitudeUseCase(year).collect {
                Log.d("HWO", "getYearGratitude -> $it")
            }
        }
    }

    fun getGratitudeRecordMonthly(yearMonth: String) {
        Log.d("HWO", "yearMonth -> $yearMonth")
        viewModelScope.launch {
            getGratitudeRecordMonthlyUseCase(yearMonth).collect {
                Log.d("HWO", "getGratitudeRecordMonthly -> $it")
            }
        }
    }
}
