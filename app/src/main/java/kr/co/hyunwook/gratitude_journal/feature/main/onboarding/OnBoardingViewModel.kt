package kr.co.hyunwook.gratitude_journal.feature.main.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.NopCollector.emit
import javax.inject.Inject
import androidx.lifecycle.ViewModel

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

): ViewModel() {

    private val _doneOnBoardingEvent = MutableSharedFlow<Unit>()
    val doneOnBoarding: SharedFlow<Unit> get() = _doneOnBoardingEvent



    fun doneOnBoarding() {
        flow {
            emit()
        }

    }


}
