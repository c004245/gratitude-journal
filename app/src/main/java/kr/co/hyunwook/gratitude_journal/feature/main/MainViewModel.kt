package kr.co.hyunwook.gratitude_journal.feature.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetTodayGratitudeRecordUseCase
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.SaveGratitudeRecordUseCase
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveGratitudeRecordUseCase: SaveGratitudeRecordUseCase,
    private val getTodayGratitudeRecordUseCase: GetTodayGratitudeRecordUseCase
) : ViewModel() {

    private val _saveDoneEvent = MutableSharedFlow<Boolean>()
    val saveDoneEvent: SharedFlow<Boolean> get() = _saveDoneEvent

    private val _todayGratitudeSummary = MutableStateFlow<TodayGratitudeSummary?>(null)
    val todayGratitudeSummary: StateFlow<TodayGratitudeSummary?> get() = _todayGratitudeSummary

    fun getTodayGratitudeRecord() {
        viewModelScope.launch {
            getTodayGratitudeRecordUseCase().collect { record ->
                Log.d("HWO", "record data -> ${record.hasWrittenToday}")
                Log.d("HWO", "record data22 -> ${record.todayGratitudeMemo}")
                Log.d("HWO", "record data33 -> ${record.consecutiveDays}")
                _todayGratitudeSummary.value = record
            }
        }
    }
    fun saveGratitudeRecord(gratitudeRecord: GratitudeRecord) {
        flow {
            emit(saveGratitudeRecordUseCase(gratitudeRecord))
        }.onEach {
            _saveDoneEvent.emit(true)
        }.catch {
            _saveDoneEvent.emit(false)
        }.launchIn(viewModelScope)

    }
}
