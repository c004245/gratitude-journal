package kr.co.hyunwook.gratitude_journal.feature.home

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getTodayGratitudeRecord()
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
                fontSize = 18.sp
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.text_home_deer_send_title),
                style = GratitudeTheme.typography.regular,
                color = yellowFF,
                fontSize = 18.sp
            )
            RemoteConfigDeerMessage(viewModel)

        }
    }
}



@Composable
fun RemoteConfigDeerMessage(viewModel: HomeViewModel) {
    val deerMessage by viewModel.deerMessage.collectAsState()

    Text(
        text = deerMessage,
        style = GratitudeTheme.typography.regular,
        fontSize = 20.sp,
        color = Color.White
    )
}