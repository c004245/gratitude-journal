package kr.co.hyunwook.gratitude_journal.feature.main.onboarding

import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purpleC4
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnBoardingScreen(
    navigateToHome: () -> Unit = {},
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {

    val pages = listOf(
        OnBoardingData(
            imageRes = R.drawable.ic_splash,
            title = "혼자가 아니에요!",
            description = "저도 매일 감사일기를 적고 있어요.\n저와 함께 매일매일 적어봐요."
        ),
        OnBoardingData(
            imageRes = R.drawable.ic_splash,
            title = "매일 쉽게 감사일기를 적을 수 있어요!",
            description = "매일 감사일기를 쌓다보면\n내가 어떤 감사일기를 적었는지\n달별로 확인이 가능해요."
        ),
        OnBoardingData(
            imageRes = R.drawable.ic_splash,
            title = "감사는 삶을 변화시키는 첫걸음이에요!",
            description = "개발자의 감사일기도 함께 공유하며 영감을 받아보세요."
        )
    )

    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onBoardingViewModel.saveOnBoardingEvent.collect {
            navigateToHome()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().height(500.dp)
            ) { page ->
                OnBoardingPage(data = pages[page])
            }

            Spacer(modifier = Modifier.height(16.dp))
            CircleIndicator(
                currentPage = pagerState.currentPage,
                pageCount = pages.size
            )

        }
        Button(
            onClick = {
                scope.launch {
                    if (pagerState.currentPage < pages.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onBoardingViewModel.showOnBoardingDone()

//                        navigateToHome()
                    }
                }

            },
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                ),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleC4
            )
        ) {
            Text(
                text = if (pagerState.currentPage < pages.size - 1) {
                    stringResource(id = R.string.text_next)
                } else {
                    stringResource(id = R.string.text_start)
                },
                style = GratitudeTheme.typography.bold,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }


}

@Composable
fun OnBoardingPage(data: OnBoardingData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(data.imageRes),
            contentDescription = null,
            modifier = Modifier.wrapContentWidth()
        )
        Text(
            text = data.title,
            style = GratitudeTheme.typography.bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = data.description,
            style = GratitudeTheme.typography.medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun CircleIndicator(
    currentPage: Int,
    pageCount: Int,
    activeColor: Color = Color.Blue,
    inActiveColor: Color = Color.Red,
    indicatorSize: Dp = 8.dp,
    spacing: Dp = 8.dp
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .background(
                        color = if (index == currentPage) activeColor else inActiveColor,
                        shape = CircleShape
                    )
            )
        }

    }
}

data class OnBoardingData(
    val imageRes: Int,
    val title: String,
    val description: String
)