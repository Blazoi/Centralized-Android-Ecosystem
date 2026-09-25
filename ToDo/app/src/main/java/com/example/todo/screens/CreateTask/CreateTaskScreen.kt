package com.example.todo.screens.CreateTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.screens.CreateTask.CreateTaskWidgets.AddButton
import com.example.todo.screens.CreateTask.CreateTaskWidgets.DateSection
import com.example.todo.screens.CreateTask.CreateTaskWidgets.DescriptionSection
import com.example.todo.screens.CreateTask.CreateTaskWidgets.GuestSection
import com.example.todo.screens.CreateTask.CreateTaskWidgets.LocationSection
import com.example.todo.screens.CreateTask.CreateTaskWidgets.TitleSection
import com.example.todo.screens.Event
import com.example.todo.screens.toDoEvents
import com.example.todo.ui.theme.RoyalGold
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import com.example.todo.ui.theme.VibrantCoral
import com.example.todo.ui.theme.primaryDark
import java.time.LocalDate
import kotlin.collections.plus

@Composable
fun CreateTask(
    modifier: Modifier = Modifier,
    createEvent: (Event) -> Unit,
    topBarTitle: (String) -> Unit
) {
    topBarTitle("Create a new Task")

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val date = LocalDate.now()
    var year by remember { mutableStateOf(date.year.toString()) }
    var month by remember { mutableStateOf(date.monthValue.toString()) }
    var day by remember { mutableStateOf(date.dayOfMonth.toString()) }

    var location by remember { mutableStateOf("") }

    var guestAdded by remember { mutableStateOf("") }
    var guestList by remember { mutableStateOf(emptyList<String>()) }

    val icon by remember { mutableStateOf(Icons.Default.Map) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = SurfaceCard,
        unfocusedContainerColor = SurfaceCard,
        disabledContainerColor = SurfaceCard,
        focusedBorderColor = TextBlack,
        unfocusedBorderColor = SubtleGrey,
        focusedLabelColor = TextBlack,
        unfocusedLabelColor = TextBlack.copy(alpha = 0.6f),
        focusedTextColor = TextBlack,
        unfocusedTextColor = TextBlack
    )

    val fieldShape = RoundedCornerShape(12.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(primaryDark)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSection(
                title = title,
                onChanged = { title = it },
                colors = fieldColors,
                shape = fieldShape,
                modifier = Modifier.weight(1f)
            )

            DescriptionSection(
                description = description,
                onChanged = { description = it },
                colors = fieldColors,
                shape = fieldShape,
                modifier = Modifier.weight(1f)
            )

            DateSection(
                day = day,
                month = month,
                year = year,
                dayChanged = { day = it },
                monthChanged = { month = it },
                yearChanged = { year = it },
                colors = fieldColors,
                shape = fieldShape,
                modifier = Modifier.weight(1f)
            )

            LocationSection(
                location = location,
                onValueChange = { location = it },
                colors = fieldColors,
                shape = fieldShape,
                modifier = Modifier.weight(1f)
            )

            GuestSection(
                guestAdded = guestAdded,
                guestList = guestList,
                onGuestAddedChange = { guestAdded = it },
                onAddGuest = { guestList += guestAdded },
                colors = fieldColors,
                shape = fieldShape,
                modifier = Modifier.weight(2.5f)
            )

            AddButton(
                title = title,
                description = description,
                date = LocalDate.of(
                    year.toIntOrNull() ?: date.year,
                    month.toIntOrNull() ?: date.monthValue,
                    day.toIntOrNull() ?: date.dayOfMonth
                ),
                location = location,
                icon = icon,
                createEvent = createEvent,
                shape = fieldShape,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp)
            )
        }
    }
}