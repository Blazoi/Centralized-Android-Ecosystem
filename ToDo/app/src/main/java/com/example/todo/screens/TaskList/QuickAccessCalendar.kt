package com.example.todo.screens.TaskList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.CanvasBackground
import com.example.todo.ui.theme.DarkViolet
import com.example.todo.ui.theme.RoyalGold
import com.example.todo.ui.theme.SoftLavender
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import com.example.todo.ui.theme.VibrantCoral
import com.example.todo.ui.theme.onTertiaryDark
import com.example.todo.ui.theme.onTertiaryLight
import com.example.todo.ui.theme.primaryDark
import com.example.todo.ui.theme.secondaryDark
import com.example.todo.ui.theme.tertiaryContainerDark
import com.example.todo.ui.theme.tertiaryDark
import com.example.todo.ui.theme.tertiaryLight
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

data class StickerColors(
    val headerBackground: Color,
    val headerText: Color,
    val bodyBackground: Color,
    val bodyText: Color
)
@Composable
fun QuickAccessCalendar(modifier: Modifier) {
    val currentDate = LocalDate.now()
    val weekOf = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    val colorSchemes = mapOf(
        "past" to StickerColors(
            headerBackground = secondaryDark,
            headerText = TextBlack.copy(alpha = 0.5f),
            bodyBackground = CanvasBackground,
            bodyText = TextBlack.copy(alpha = 0.4f)
        ), "present" to StickerColors(
            headerBackground = primaryDark,
            headerText = TextBlack,
            bodyBackground = SurfaceCard,
            bodyText = TextBlack
        ), "future" to StickerColors(
            headerBackground = SoftLavender,
            headerText = DarkViolet,
            bodyBackground = SurfaceCard,
            bodyText = DarkViolet
        ), "weekend" to StickerColors(
            headerBackground = tertiaryLight,
            headerText = SurfaceCard,
            bodyBackground = tertiaryLight.copy(alpha = 0.1f),
            bodyText = onTertiaryDark
        ), "weekendPast" to StickerColors(
            headerBackground = tertiaryDark,
            headerText = TextBlack,
            bodyBackground = tertiaryDark.copy(alpha = 0.15f),
            bodyText = TextBlack.copy(alpha = 0.4f)
        )
    )

    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val headerFormatter = DateTimeFormatter.ofPattern("'Week of' EEE, MMM d")
        Text(
            text = weekOf.format(headerFormatter),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextBlack
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 0..6) {
                val day = weekOf.plusDays(i.toLong())
                val isWeekend =
                    day.dayOfWeek == DayOfWeek.SATURDAY || day.dayOfWeek == DayOfWeek.SUNDAY
                val isToday = day == currentDate
                val isPast = day < currentDate

                val stickerColors = when {
                    isToday -> colorSchemes.getValue("present")
                    isPast && !isWeekend -> colorSchemes.getValue("past")
                    !isPast && !isWeekend -> colorSchemes.getValue("future")
                    !isPast -> colorSchemes.getValue("weekend")
                    else -> colorSchemes.getValue("weekendPast")
                }

                DateSticker(
                    date = day, colors = stickerColors, modifier = if (isToday) Modifier
                        .border(
                            2.dp, Color.Black, RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                    else Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Composable
fun DateSticker(
    date: LocalDate, colors: StickerColors, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f)
                .background(colors.headerBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfWeek.name.take(3),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colors.headerText
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.bodyBackground)
                .padding(vertical = 8.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colors.bodyText
            )
        }
    }
}