package kr.co.hyunwook.gratitude_journal.feature.main.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.GetIsShowOnBoardingUseCase
import kr.co.hyunwook.gratitude_journal.core.domain.usecase.SaveShowOnBoardingUseCase
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveShowOnBoardingUseCase: SaveShowOnBoardingUseCase
): ViewModel() {

    private val _saveOnBoardingEvent = MutableSharedFlow<Unit>()
    val saveOnBoardingEvent: SharedFlow<Unit> get() = _saveOnBoardingEvent



    fun showOnBoardingDone() {
        flow {
            emit(saveShowOnBoardingUseCase())
        }.onEach {
            _saveOnBoardingEvent.emit(Unit)
        }.launchIn(viewModelScope)

    }


}
