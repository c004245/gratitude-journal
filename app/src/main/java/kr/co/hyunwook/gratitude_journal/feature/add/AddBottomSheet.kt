package kr.co.hyunwook.gratitude_journal.feature.add

import kr.co.hyunwook.gratitude_journal.core.database.entity.GratitudeRecord
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetContent(onSaveGratitude: (GratitudeRecord) -> Unit) {
    val sheetHeight = 580.dp

    Column(
        modifier = Modifier.fillMaxWidth().height(sheetHeight).padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        )
    }
}