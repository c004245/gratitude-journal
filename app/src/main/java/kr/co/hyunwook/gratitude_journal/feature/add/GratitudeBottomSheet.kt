package kr.co.hyunwook.gratitude_journal.feature.add

import kr.co.hyunwook.gratitude_journal.R
import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import kr.co.hyunwook.gratitude_journal.core.designsystem.theme.black46
import kr.co.hyunwook.gratitude_journal.ui.theme.GratitudeTheme
import kr.co.hyunwook.gratitude_journal.ui.theme.black24
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFF
import kr.co.hyunwook.gratitude_journal.ui.theme.yellowFFOpacity30
import kr.co.hyunwook.gratitude_journal.util.getGratitudeEmojis
import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GratitudeBottomSheet(onSaveGratitude: (GratitudeRecord) -> Unit, onClose: () -> Unit) {
    var gratitudeText by remember { mutableStateOf("") }

    val saveButtonBackground = if (gratitudeText.isEmpty()) yellowFFOpacity30 else yellowFF

    val isKeyboardVisible = WindowInsets.isImeVisible

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(580.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopSection(
                onClose = {
                    onClose()
                }
            )
            GratitudeTextField(
                gratitudeText = gratitudeText,
                onTextChange = {
                    gratitudeText = it
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isKeyboardVisible) {
                        Image(
                            painter = painterResource(R.drawable.ic_add_bottom_background),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 48.dp)
                        )
                    }
                    SaveGratitudeButton(
                        gratitudeText = gratitudeText,
                        buttonBackgroundColor = saveButtonBackground,
                        onSaveGratitude = {
                            onSaveGratitude(it)
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = if (isKeyboardVisible) 16.dp else 20.dp)
                            .imePadding()
                    )
                }
            }
        }
    }

}


@Composable
private fun TopSection(onClose: () -> Unit) {
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
                    onClose()
                }
        )
    }
}

@Composable
fun GratitudeTextField(gratitudeText: String, onTextChange: (String) -> Unit) {
    val backgroundColor = if (gratitudeText.isEmpty()) black46 else yellowFF
    val textColor = if (gratitudeText.isEmpty()) Color.White else black24
    val tailState = if (gratitudeText.isEmpty()) R.drawable.ic_my_tail else R.drawable.ic_my_tail_done

    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }

    // 상위 Box로 감싸기
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 4.dp, end = 4.dp)
    ) {
        // 오른쪽 상단에 ic_deer_tail 이미지 배치
        Image(
            painter = painterResource(id = tailState),
            contentDescription = null,
            modifier = Modifier
                .size(15.dp) // 이미지 크기 설정
                .align(Alignment.TopEnd) // 오른쪽 상단에 배치
                .offset(x = 10.dp, y = 13.dp) // 위치 미세 조정
        )

        // 메인 Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = RoundedCornerShape(15.dp))
                .padding(top = 10.dp, bottom = 10.dp, start = 15.dp)
        ) {
            BasicTextField(
                value = gratitudeText,
                onValueChange = onTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused.value = focusState.isFocused
                    },
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 14.sp,
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(0.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (gratitudeText.isEmpty() && !isFocused.value) {
                            Text(
                                text = stringResource(R.string.text_add_bottom_hint),
                                color = Color.White,
                                style = GratitudeTheme.typography.regular,
                                fontSize = 14.sp
                            )
                        }
                        Box(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            innerTextField()
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun SaveGratitudeButton(
    gratitudeText: String,
    onSaveGratitude: (GratitudeRecord) -> Unit,
    buttonBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        {
            val record = GratitudeRecord(
                gratitudeMemo = gratitudeText,
                timeStamp = System.currentTimeMillis(),
                gratitudeType = getGratitudeEmojis()
            )
            onSaveGratitude(record)

        },
        modifier = modifier,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor,
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

