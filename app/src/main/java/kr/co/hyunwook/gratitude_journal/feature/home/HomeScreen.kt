package kr.co.hyunwook.gratitude_journal.feature.home

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.TodayGratitudeSummary
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.black46
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import android.graphics.Paint.Align
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    todayGratitudeSummary: TodayGratitudeSummary?,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(start = 20.dp, top = 13.dp),
                    text = stringResource(R.string.text_splash_bottom),
                    style = GratitudeTheme.typography.regular,
                    color = Color.White,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(16.dp))
                DeerGratitudeWidget(viewModel)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    MyGratitudeWidget(
                        viewModel
                    )
                }
            }

            TodayGratitdeWidget(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(bottom = 54.dp),
                todayGratitudeSummary = todayGratitudeSummary
            )
        }

    }
}


@Composable
fun DeerGratitudeWidget(viewModel: HomeViewModel) {
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = stringResource(R.string.text_home_deer_send_title),
        style = GratitudeTheme.typography.regular,
        color = yellowFF,
        fontSize = 18.sp
    )
    RemoteConfigDeerMessage(viewModel)
}


@Composable
fun RemoteConfigDeerMessage(viewModel: HomeViewModel) {
    val deerMessage by viewModel.deerMessage.collectAsState()

    Box(
        modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 40.dp)
            .background(yellowFF, shape = RoundedCornerShape(15.dp)).padding(15.dp)
    ) {

        Text(
            text = deerMessage,
            style = GratitudeTheme.typography.regular,
            fontSize = 14.sp,
            color = black24
        )
    }
}


@Composable
fun MyGratitudeWidget(viewModel: HomeViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth() // 우측 상단 정렬
    ) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(R.string.text_home_deer_send_title),
            style = GratitudeTheme.typography.regular,
            color = yellowFF,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        MyGratitudeMessage(viewModel)
    }
}

@Composable
fun MyGratitudeMessage(viewModel: HomeViewModel) {

    Box(
        modifier = Modifier.fillMaxWidth().padding(start = 40.dp)
            .background(black46, shape = RoundedCornerShape(15.dp)).padding(15.dp)
    ) {

        Text(
            text = stringResource(R.string.text_home_my_send_message_default),
            style = GratitudeTheme.typography.regular,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun TodayGratitdeWidget(modifier: Modifier = Modifier, todayGratitudeSummary: TodayGratitudeSummary?) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text  = "${todayGratitudeSummary?.consecutiveDays}일 연속",
                style = GratitudeTheme.typography.regular,
                color = yellowFF,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (todayGratitudeSummary?.hasWrittenToday == false) {
                    "아직 작성 기록이 없어요"
                } else {
                    "감사 일기 작성 중"
                },

                style = GratitudeTheme.typography.regular,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center

            )
        }

    }

}