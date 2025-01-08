package kr.co.hyunwook.gratitude_journal.feature.add

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetContent(onSaveGratitude: (GratitudeRecord) -> Unit) {
    val sheetHeight = 580.dp

    Column(
        modifier = Modifier.fillMaxWidth().height(sheetHeight).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center 
        ) {
            Text(
                text = stringResource(id = R.string.text_add_bottom_title),
                style = GratitudeTheme.typography.regular,
                fontSize = 18.sp,
                color = yellowFF
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                tint = yellowFF,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        // 클릭 이벤트
                    }
            )
        }

        Button(
            onClick = {
                val record = GratitudeRecord(
                    
                )
                onSaveGratitude

            },
        ) {
            Text(
                text = stringResource(id = R.string.text_splash),
                style = GratitudeTheme.typography.regular,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}