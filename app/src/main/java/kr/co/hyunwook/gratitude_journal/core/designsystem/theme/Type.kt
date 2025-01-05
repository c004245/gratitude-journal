package kr.co.hyunwook.gratitude_journal.core.designsystem.theme

import kr.co.hyunwook.gratitude_journal.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp



val gowunRegular = FontFamily(Font(R.font.gowundodum_regular, FontWeight.Normal))

@Stable
class GratitudeTypography internal constructor(
    regular: TextStyle

) {

    var regular: TextStyle by mutableStateOf(regular)
        private set

    fun copy(
        regular: TextStyle = this.regular
    ): GratitudeTypography = GratitudeTypography(regular)

    fun update(other: GratitudeTypography) {
        regular = other.regular
    }
}

    fun gratitudeTextStyle(
        fontFamily: FontFamily,
        fontWeight: FontWeight,
        fontSize: TextUnit,
        lineHeight: TextUnit,
    ): TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = lineHeight,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )


    @Composable
    fun gratitudeTypography(): GratitudeTypography {
        return GratitudeTypography(
            regular = gratitudeTextStyle(
                fontFamily = gowunRegular,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                lineHeight = 25.sp

            )
        )
    }
