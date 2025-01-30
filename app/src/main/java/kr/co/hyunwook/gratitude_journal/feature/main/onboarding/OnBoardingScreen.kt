package kr.co.hyunwook.gratitude_journal.feature.main.onboarding

import kotlinx.coroutines.launch
import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.purpleC4
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFFOpacity30
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
            imageRes = R.drawable.ic_onboarding_1,
            title = stringResource(R.string.text_onboarding_title1),
            description =  stringResource(R.string.text_onboarding_description1),
        ),
        OnBoardingData(
            imageRes = R.drawable.ic_onboarding_2,
            title = stringResource(R.string.text_onboarding_title2),
            description =  stringResource(R.string.text_onboarding_description2),
        ),
        OnBoardingData(
            imageRes = R.drawable.ic_onboarding_3,
            title = stringResource(R.string.text_onboarding_title3),
            description =  stringResource(R.string.text_onboarding_description3),
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
        modifier = Modifier.fillMaxSize().background(black24)
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
                containerColor = yellowFF
            )
        ) {
            Text(
                text = if (pagerState.currentPage < pages.size - 1) {
                    stringResource(id = R.string.text_next)
                } else {
                    stringResource(id = R.string.text_start)
                },
                style = GratitudeTheme.typography.regular,
                color = black24,
                fontSize = 15.sp
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
        Spacer(Modifier.height(40.dp))
        Text(
            text = data.title,
            style = GratitudeTheme.typography.regular,
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.description,
            style = GratitudeTheme.typography.regular,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun CircleIndicator(
    currentPage: Int,
    pageCount: Int,
    activeColor: Color = yellowFF,
    inActiveColor: Color = yellowFFOpacity30,
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