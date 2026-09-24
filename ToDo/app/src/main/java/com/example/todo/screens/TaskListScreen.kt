package com.example.todo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import kotlin.math.hypot

@Composable
fun TaskList(
    modifier: Modifier,
    eventsList: List<Event>,
    topBarTitle: (String) -> Unit,
    moreInfo: (Int) -> Unit
) {

    topBarTitle("Welcome Back, Jack")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceCard),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuickAccessCalendar(Modifier.weight(1f))
        MainToDoList(eventsList, Modifier.weight(3f), { moreInfo(it) })
    }
}

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
            headerBackground = SubtleGrey,
            headerText = TextBlack.copy(alpha = 0.5f),
            bodyBackground = CanvasBackground,
            bodyText = TextBlack.copy(alpha = 0.4f)
        ), "present" to StickerColors(
            headerBackground = Color(0x55367562),
            headerText = TextBlack,
            bodyBackground = SurfaceCard,
            bodyText = TextBlack
        ), "future" to StickerColors(
            headerBackground = SoftLavender,
            headerText = DarkViolet,
            bodyBackground = SurfaceCard,
            bodyText = DarkViolet
        ), "weekend" to StickerColors(
            headerBackground = VibrantCoral,
            headerText = SurfaceCard,
            bodyBackground = VibrantCoral.copy(alpha = 0.1f),
            bodyText = VibrantCoral
        ), "weekendPresent" to StickerColors(
            headerBackground = RoyalGold,
            headerText = TextBlack,
            bodyBackground = VibrantCoral.copy(alpha = 0.15f),
            bodyText = TextBlack
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
                    isToday && !isWeekend -> colorSchemes.getValue("present")
                    isPast -> colorSchemes.getValue("past")
                    !isToday && !isPast && !isWeekend -> colorSchemes.getValue("future")
                    isWeekend && isToday -> colorSchemes.getValue("weekendPresent")
                    else -> colorSchemes.getValue("weekend")
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
                .background(colors.headerBackground), contentAlignment = Alignment.Center
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

@Composable
fun MainToDoList(eventsList: List<Event>, modifier: Modifier, moreInfo: (Int) -> Unit) {
    val areEvents = eventsList.isNotEmpty()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color(0xFFB3C5BD))
            .padding(10.dp),
        verticalArrangement = if (areEvents) Arrangement.spacedBy(10.dp) else Arrangement.Center
    ) {
        if (areEvents) {
            itemsIndexed(eventsList) { index, event ->
                ToDoItem(
                    index,
                    event.title,
                    event.description,
                    event.date,
                    event.location,
                    event.icon,
                    event.isCompleted,
                    { event.isCompleted = !event.isCompleted },
                    { moreInfo(index) })
            }
        } else {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks for now...",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xAA1B4D3E)
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoItem(
    id: Int,
    title: String,
    description: String,
    date: LocalDate,
    location: String,
    icon: ImageVector,
    isCompleted: Boolean,
    onCheckedChange: () -> Unit,
    moreInfo: (Int) -> Unit,
//    changePosition: () -> Unit
) {
    var isChecked by remember { mutableStateOf(isCompleted) }

    val context = LocalContext.current
    val shape = RoundedCornerShape(16.dp)
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = SubtleGrey, shape = shape)
            .clip(shape)
            .background(SurfaceCard)
            .clickable { moreInfo(id) }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked, onCheckedChange = {
                onCheckedChange()
//                changePosition()

                isChecked = !isChecked
            }, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF1B4D3E),
                uncheckedColor = TextBlack.copy(alpha = 0.4f),
                checkmarkColor = SurfaceCard
            )
        )

        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isChecked) TextBlack.copy(alpha = 0.4f) else TextBlack,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (description.isNotBlank()) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(200.dp),
                    color = if (isChecked) TextBlack.copy(alpha = 0.3f) else TextBlack.copy(alpha = 0.8f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date.format(dateFormatter),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextBlack.copy(alpha = 0.6f)
                )

                if (location.isNotBlank()) {
                    Text(
                        text = "•  $location",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextBlack.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        IconButton(
            onClick = {
                Toast.makeText(
                    context, date.dayOfWeek.name, Toast.LENGTH_SHORT
                ).show()
            }, modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CanvasBackground)
        ) {
            Icon(
                imageVector = icon, contentDescription = null, tint = Color(0xFF1B4D3E)
            )
        }
    }
}