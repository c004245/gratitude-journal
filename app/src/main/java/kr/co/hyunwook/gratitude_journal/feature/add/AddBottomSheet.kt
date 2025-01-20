package kr.co.hyunwook.gratitude_journal.feature.add

import kr.co.hyunwook.gratitude_journal.feature.main.MainViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BottomSheetContent(
    viewModel: MainViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val sheetHeight = 580.dp

    var currentStep by remember { mutableStateOf(Step.GRATITUDE) }

    LaunchedEffect(Unit) {
        viewModel.saveDoneEvent.collect { isSuccess ->
            if (isSuccess) {
                currentStep = Step.DONE
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth().height(sheetHeight).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (currentStep) {
            Step.GRATITUDE -> {
                GratitudeBottomSheet(
                    onSaveGratitude = { gratitudeRecord ->
                        currentStep = Step.DONE
                        viewModel.saveGratitudeRecord(gratitudeRecord)
                    },
                    onClose = {
                        onClose()
                    }
                )
            }
            Step.DONE -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                DoneBottomSheet(
                    onDone = {
                        onClose()
                    }
                )
            }
        }
    }
}

@Stable
fun BalloonShape() = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp)


enum class Step {
    GRATITUDE,
    DONE
}