package kr.co.hyunwook.gratitude_journal.feature.total

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.blue8A
import kr.co.hyunwook.gratitude_journal.ui.theme.blueA3
import kr.co.hyunwook.gratitude_journal.ui.theme.blueA7
import kr.co.hyunwook.gratitude_journal.ui.theme.grayBB
import kr.co.hyunwook.gratitude_journal.ui.theme.green97
import kr.co.hyunwook.gratitude_journal.ui.theme.greenA1
import kr.co.hyunwook.gratitude_journal.ui.theme.orangeEE
import kr.co.hyunwook.gratitude_journal.ui.theme.orangeF0
import kr.co.hyunwook.gratitude_journal.ui.theme.pinkFF
import kr.co.hyunwook.gratitude_journal.ui.theme.purpleA1
import kr.co.hyunwook.gratitude_journal.ui.theme.redDD
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowF8
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import java.util.Calendar.YEAR
import java.util.Calendar.getInstance
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalScreen(
    viewModel: TotalViewModel = hiltViewModel(),
    navigateToTotalMonthly: (String) -> Unit = {}
) {

    val calendar = remember { getInstance() }
    var currentYear by remember { mutableIntStateOf(calendar.get(YEAR)) }

    val visibleMonths by viewModel.monthGratitude.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getYearGratitudeRecord(currentYear.toString())
    }
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
                    fontSize = 18.sp,
                )

                TotalMonthly(
                    year = currentYear,
                    onPreviousYear = {
                        calendar.add(YEAR, -1)
                        currentYear = calendar.get(YEAR)
                        viewModel.getYearGratitudeRecord(currentYear.toString())


                    },
                    onNextYear = {
                        calendar.add(YEAR, 1)
                        currentYear = calendar.get(YEAR)
                        viewModel.getYearGratitudeRecord(currentYear.toString())


                    })

                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.text_total_title),
                        style = GratitudeTheme.typography.regular,
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
                TotalGridView(
                    visibleMonths,
                    year = currentYear,
                    navigateToTotalMonthly = navigateToTotalMonthly
                )

            }
        }
    }
}

@Composable
fun TotalMonthly(
    year: Int,
    onPreviousYear: () -> Unit,
    onNextYear: () -> Unit
) {
    val context = LocalContext.current
    val text = context.getString(R.string.text_gratitude_yearly, year)

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.ic_total_previous),
            contentDescription = null,
            modifier = Modifier.clickable {
                onPreviousYear()
            })

        Text(
            text = text,
            style = GratitudeTheme.typography.regular,
            color = yellowFF,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(R.drawable.ic_total_next),
            contentDescription = null,
            modifier = Modifier.clickable {
                onNextYear()
            }
        )
    }
}

@Composable
fun TotalGridView(
    visibleMonths: List<Int>,
    year: Int,
    navigateToTotalMonthly: (String) -> Unit = {}
) {


    val monthlyItems = listOf(
        TotalGridItem(1, purpleA1, R.drawable.ic_total_jan),
        TotalGridItem(2, greenA1, R.drawable.ic_total_feb),
        TotalGridItem(3, green97, R.drawable.ic_total_march),
        TotalGridItem(4, pinkFF, R.drawable.ic_total_april),
        TotalGridItem(5, orangeF0, R.drawable.ic_total_may),
        TotalGridItem(6, blueA3, R.drawable.ic_total_june),
        TotalGridItem(7, yellowF8, R.drawable.ic_total_july),
        TotalGridItem(8, blueA7, R.drawable.ic_total_august),
        TotalGridItem(9, blue8A, R.drawable.ic_total_secember),
        TotalGridItem(10, orangeEE, R.drawable.ic_total_october),
        TotalGridItem(11, grayBB, R.drawable.ic_total_november),
        TotalGridItem(12, redDD, R.drawable.ic_total_december),
    )

    val filteredItem = monthlyItems.filter { visibleMonths.contains(it.month) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredItem) { item ->
            Box(
                modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(15.dp))
                    .background(item.backgroundColor)
                    .clickable {
                        navigateToTotalMonthly("$year-${String.format("%02d", item.month)}")
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.month.toString(),
                            style = GratitudeTheme.typography.regular,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.width(2.dp))
                        Text(
                            text = "월",
                            style = GratitudeTheme.typography.regular,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(0.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(item.backgroundIcon),
                        contentDescription = null

                    )
                }
            }
        }
    }
}

data class TotalGridItem(val month: Int, val backgroundColor: Color, val backgroundIcon: Int)