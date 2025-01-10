package kr.co.hyunwook.gratitude_journal.feature.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.SaveGratitudeRecordUseCase
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveGratitudeRecordUseCase: SaveGratitudeRecordUseCase
) : ViewModel() {

    private val _saveDoneEvent = MutableSharedFlow<Boolean>()
    val saveDoneEvent: SharedFlow<Boolean> get() = _saveDoneEvent

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
