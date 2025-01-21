package kr.co.hyunwook.gratitude_journal.feature.total

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.GratitudeRecordMonthly
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import android.util.Log
import kotlin.math.absoluteValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalMonthlyScreen(
    viewModel: TotalViewModel = hiltViewModel(),
    selectYearMonth: String
) {

    val monthlyGratitudeRecords by viewModel.monthlyGratitudeRecords.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getGratitudeRecordMonthly(selectYearMonth)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomMonthlyGratitudeViewPager(
            modifier = Modifier.padding(),
            gratitudeRecordMonthlyItem = monthlyGratitudeRecords
        )

    }
}


@Composable
fun CustomMonthlyGratitudeViewPager(modifier: Modifier, gratitudeRecordMonthlyItem: List<GratitudeRecordMonthly>) {
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
        modifier = modifier.fillMaxWidth().padding(vertical = 40.dp),
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
                    .width(itemWidth) // 아이템의 너비
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
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
    }
