package kr.co.hyunwook.gratitude_journal.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetTodayGratitudeRecordUseCase
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayGratitudeRecordUseCase: GetTodayGratitudeRecordUseCase
): ViewModel() {

    fun getTodayGratitudeRecord() {
        viewModelScope.launch {
            getTodayGratitudeRecordUseCase().collect { record ->
                Log.d("HWO", "record data -> ${record.hasWrittenToday}")
                Log.d("HWO", "record data22 -> ${record.todayGratitudeMemo}")
                Log.d("HWO", "record data33 -> ${record.todayGratitudeMemo}")
            }
        }
    }

}
