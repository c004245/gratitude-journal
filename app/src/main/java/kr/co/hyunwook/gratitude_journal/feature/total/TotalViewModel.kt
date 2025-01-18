package kr.co.hyunwook.gratitude_journal.feature.total

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetYearTotalGratitudeUseCase
import android.util.Log
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class TotalViewModel @Inject constructor(
    private val getYearTotalGratitudeUseCase: GetYearTotalGratitudeUseCase
): ViewModel() {



    fun getYearGratitudeRecord(year: String) {
        viewModelScope.launch {
            getYearTotalGratitudeUseCase(year).collect {
                Log.d("HWO", "getYearGratitude -> $it")
            }
        }

    }
}
