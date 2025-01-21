package kr.co.hyunwook.gratitude_journal.feature.total

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordMonthly
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import kotlin.math.absoluteValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalMonthlyScreen(
    viewModel: TotalViewModel = hiltViewModel(),
    selectYearMonth: String,
    navigateToTotal: () -> Unit,
) {

    val monthlyGratitudeRecords by viewModel.monthlyGratitudeRecords.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getGratitudeRecordMonthly(selectYearMonth)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 13.dp),
                text = stringResource(R.string.text_splash_bottom),
                style = GratitudeTheme.typography.regular,
                color = Color.White,
                fontSize = 18.sp,
            )
            TotalMonthlyTitle(
                title = formatYearMonth(selectYearMonth),
                onClose = {
                    navigateToTotal()
                }
            )
            CustomMonthlyGratitudeViewPager(
                gratitudeRecordMonthlyItem = monthlyGratitudeRecords
            )
        }
    }
}
@Composable
fun TotalMonthlyTitle(
    title: String,
    onClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_total_previous),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart) // 왼쪽 중앙에 배치
                .clickable {
                    onClose()
                }
        )

        Text(
            text = title,
            style = GratitudeTheme.typography.regular,
            color = yellowFF,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}




@Composable
fun CustomMonthlyGratitudeViewPager(
    gratitudeRecordMonthlyItem: List<GratitudeRecordMonthly>
) {
    val listState = rememberLazyListState()
    val currentPage by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            visibleItems.minByOrNull {
                (it.offset + it.size / 2 - layoutInfo.viewportEndOffset / 2).absoluteValue
            }?.index ?: 0
        }
    }


    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth - (2 * 40.dp) - 16.dp

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(
            start = (screenWidth - itemWidth) / 2,
            end = (screenWidth - itemWidth) / 2
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        verticalAlignment = Alignment.Top
    ) {
        itemsIndexed(gratitudeRecordMonthlyItem) { index, item ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val scale = 1f - (currentPage - index).absoluteValue * 0.15f
                        scaleX = scale
                        scaleY = scale
                    }
                    .width(itemWidth)
                    .wrapContentHeight()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(14.dp),
                        clip = true
                    )
                    .background(yellowFF, RoundedCornerShape(14.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight().padding(vertical = 20.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.text_today_gratitude),
                        style = GratitudeTheme.typography.regular,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = item.gratitudeMemo,
                        style = GratitudeTheme.typography.regular,
                        fontSize = 14.sp,
                        color = black24
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = item.gratitudeType,
                        style = GratitudeTheme.typography.regular,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

fun formatYearMonth(selectYearMonth: String): String {
    val parts = selectYearMonth.split("-")
    val year = parts[0]
    val month = parts[1].toInt() // 월을 정수로 변환

    return "${year}년의 ${month}월" // 원하는 형식으로 반환
}
