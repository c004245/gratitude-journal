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
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordDao
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetTodayGratitudeRecordUseCase
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    private val _deerMessage = MutableStateFlow("디어에 메세지를 가져오고있어요.")
    val deerMessage: StateFlow<String> = _deerMessage


    init {
        fetchDeerMessage()
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
