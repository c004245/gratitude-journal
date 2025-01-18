package kr.co.hyunwook.gratitude_journal.feature.total

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TotalScreen(
    viewModel: TotalViewModel = hiltViewModel()
) {

    val calendar = remember { getInstance() }
    var currentYear by remember { mutableIntStateOf(calendar.get(YEAR)) }


    LaunchedEffect(Unit) {
        viewModel.getYearGratitudeRecord(currentYear.toString())
    }
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
                TotalGridView()

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
            },

            )

        Text(
            text = "${year}년의 감사일기",
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
fun TotalGridView() {
    val itemCount = 12

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(itemCount) { index ->
            Box(
                modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray)
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {

            }
        }
    }

}