package kr.co.hyunwook.gratitude_journal.feature.main.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
): ViewModel() {

    private val _sideEffects = MutableSharedFlow<SplashSideEffect>()
    val sideEffects: SharedFlow<SplashSideEffect> get() = _sideEffects.asSharedFlow()

    fun showSplash() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            _sideEffects.emit(SplashSideEffect.NavigateToOnBoarding)
        }
    }


    companion object {
        const val SPLASH_DURATION = 500L
    }
}