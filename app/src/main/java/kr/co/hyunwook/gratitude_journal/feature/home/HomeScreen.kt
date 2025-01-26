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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    todayGratitudeSummary: TodayGratitudeSummary?,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize().background(black24)
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
                        todayGratitudeSummary = todayGratitudeSummary
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
            .wrapContentSize()
    ) {
        // 말풍선 박스
        Box(
            modifier = Modifier
                .background(
                    color = yellowFF,
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(15.dp)
                .align(Alignment.TopCenter) // Align text box in the center for better layout
        ) {
            Text(
                text = deerMessage,
                style = GratitudeTheme.typography.regular,
                fontSize = 14.sp,
                color = black24
            )
        }

        // 꼬리 이미지
        Image(
            painter = painterResource(id = R.drawable.ic_deer_tail), // 꼬리 이미지 리소스 사용
            contentDescription = null,
            modifier = Modifier
                .size(15.dp) // 꼬리 이미지 크기 조정
                .align(Alignment.TopStart) // 박스 왼쪽 상단에 배치
                .offset(x = (-10).dp, y = (13).dp) // 이미지 위치 조정
        )
        // 오른쪽 상단 이미지
        Image(
            painter = painterResource(id = R.drawable.ic_deer_character), // 오른쪽 상단 이미지 리소스
            contentDescription = null,
            modifier = Modifier
                .size(37.dp) // 이미지 크기 조정
                .align(Alignment.TopEnd) // 박스 오른쪽 상단에 배치
                .offset(x = (-26).dp, y = (-32).dp) // 이미지 위치 조정
        )
    }
}


@Composable
fun MyGratitudeWidget(todayGratitudeSummary: TodayGratitudeSummary?) {
    Column(
        modifier = Modifier.fillMaxWidth() // 우측 상단 정렬
    ) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(R.string.text_home_my_send_title),
            style = GratitudeTheme.typography.regular,
            color = yellowFF,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        MyGratitudeMessage(todayGratitudeSummary)
    }
}

@Composable
fun MyGratitudeMessage(todayGratitudeSummary: TodayGratitudeSummary?) {
    val hasWrittenToday = todayGratitudeSummary?.hasWrittenToday == true
    val backgroundColor = if (hasWrittenToday) yellowFF else black46
    val tailState = if (hasWrittenToday) R.drawable.ic_my_tail_done else R.drawable.ic_my_tail
    val textColor = if (hasWrittenToday) black24 else Color.White
    val messageText = todayGratitudeSummary?.todayGratitudeMemo
        ?: stringResource(R.string.text_home_my_send_message_default)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = RoundedCornerShape(15.dp))
                .padding(15.dp)
        ) {
            Text(
                text = messageText,
                style = GratitudeTheme.typography.regular,
                fontSize = 14.sp,
                color = textColor
            )
        }

        Image(
            painter = painterResource(id = tailState),
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.TopEnd)
                .offset(x = 10.dp, y = 13.dp)
        )
    }
}



@Composable
fun TodayGratitdeWidget(
    modifier: Modifier = Modifier,
    todayGratitudeSummary: TodayGratitudeSummary?
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${todayGratitudeSummary?.consecutiveDays}일 연속",
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