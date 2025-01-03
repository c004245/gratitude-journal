package kr.co.hyunwook.gratitude_journal.feature.main.splash

sealed class SplashSideEffect {
    data object NavigateToHome: SplashSideEffect()
    data object NavigateToOnBoarding: SplashSideEffect()
}
