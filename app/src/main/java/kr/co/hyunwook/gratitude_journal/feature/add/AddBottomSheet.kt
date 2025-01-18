package kr.co.hyunwook.gratitude_journal.feature.add

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.black46
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFFOpacity30
import kr.co.hyunwook.gratitude_journal.util.getGratitudeEmojis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetContent(onSaveGratitude: (GratitudeRecord) -> Unit) {
    val sheetHeight = 580.dp

    var gratitudeText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().height(sheetHeight).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TopSection()
        GratitudeTextField(
            gratitudeText = gratitudeText ,
            onTextChange = {
                gratitudeText = it
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth().weight(1f).padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_add_bottom_background),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 48.dp)
            )


            SaveGratitudeButton(onSaveGratitude)
        }


    }
}


@Composable
private fun TopSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
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
                    // 닫기 버튼 클릭 이벤트
                }
        )
    }
}

@Composable
fun GratitudeTextField(gratitudeText: String, onTextChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) } // 포커스 상태

    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 4.dp, end = 4.dp)
            .background(black46, shape = RoundedCornerShape(15.dp)).padding(15.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .clip(BalloonShape())
                .background(black46),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 텍스트 필드
            OutlinedTextField(
                onValueChange = {
                },
                value = gratitudeText,
                placeholder = {
                    Text(
                        text = "오늘 감사한 내용을 적어보이",
                        color = Color.LightGray,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent, // 내부 테두리 제거
                    unfocusedBorderColor = Color.Transparent, // 내부 테두리 제거
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Gray
                )
            )
        }

        // 작성 완료 버튼
        if (isFocused && gratitudeText.isNotEmpty()) {
            Button(
                onClick = {
                    // 완료 버튼 클릭 로직
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEB3B) // 노란색
                ),
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "작성 완료",
                    color = Color.Black,
                )
            }
        }
    }
}

@Composable
fun SaveGratitudeButton(onSaveGratitude: (GratitudeRecord) -> Unit) {
    Button(
        {
            val record = GratitudeRecord(
                gratitudeMemo = "테스트 메모",
                timeStamp = System.currentTimeMillis(),
                gratitudeType = getGratitudeEmojis()
            )
            onSaveGratitude(record)

        },
        Modifier.fillMaxWidth().height(47.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = yellowFFOpacity30,
            contentColor = black24,
        ),
    ) {
        Text(
            text = stringResource(id = R.string.text_send),
            style = GratitudeTheme.typography.regular,
            color = black24,
            fontSize = 15.sp
        )
    }
}


@Stable
fun BalloonShape() = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp  )