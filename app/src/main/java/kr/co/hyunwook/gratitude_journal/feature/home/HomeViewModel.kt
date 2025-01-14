package kr.co.hyunwook.gratitude_journal.feature.home

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _deerMessage = MutableStateFlow("디어에 메세지를 가져오고있어요.")
    val deerMessage: StateFlow<String> = _deerMessage

    init {
        fetchDeerMessage()
    }

    fun getTodayGratitudeRecord() {
        viewModelScope.launch {
            getTodayGratitudeRecordUseCase().collect { record ->
                Log.d("HWO", "record data -> ${record.hasWrittenToday}")
                Log.d("HWO", "record data22 -> ${record.todayGratitudeMemo}")
                Log.d("HWO", "record data33 -> ${record.todayGratitudeMemo}")
            }
        }
    }

    private fun fetchDeerMessage() {
        viewModelScope.launch {
            val remoteConfig = Firebase.remoteConfig
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _deerMessage.value = remoteConfig.getString("deer_message")
                } else {
                    _deerMessage.value = "디어에 메세지를 가져오고 있어요."
                }
            }
        }
    }

}
