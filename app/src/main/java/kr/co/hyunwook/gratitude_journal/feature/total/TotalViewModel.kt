package kr.co.hyunwook.gratitude_journal.feature.total

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordMonthly
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

    private val _monthlyGratitudeRecords = MutableStateFlow<List<GratitudeRecordMonthly>>(emptyList())
    val monthlyGratitudeRecords: StateFlow<List<GratitudeRecordMonthly>> get() = _monthlyGratitudeRecords
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
                _monthlyGratitudeRecords.value = it
            }
        }
    }
}
