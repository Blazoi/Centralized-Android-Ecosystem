package com.example.todo.screens

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
import com.example.todo.ui.theme.RoyalGold
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import com.example.todo.ui.theme.VibrantCoral
import java.time.LocalDate
import kotlin.collections.plus

@Composable
fun CreateTask(
    modifier: Modifier = Modifier,
    createEvent: (Event) -> Unit,
    topBarTitle: (String) -> Unit
) {
    topBarTitle("Create a new Task")

    var title by remember { mutableStateOf("New Task") }
    var description by remember { mutableStateOf("Task Description Of DOom because of how long this desccription is") }

    val date = LocalDate.now()
    var year by remember { mutableStateOf(date.year.toString()) }
    var month by remember { mutableStateOf(date.monthValue.toString()) }
    var day by remember { mutableStateOf(date.dayOfMonth.toString()) }

    var location by remember { mutableStateOf("Location X") }

    var guestAdded by remember { mutableStateOf("test") }
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
            .background(Color(0xFFB3C5BD))
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

@Composable
fun TitleSection(
    title: String,
    onChanged: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = title,
        onValueChange = onChanged,
        label = { Text(text = "Nom de la tâche") },
        colors = colors,
        shape = shape,
        singleLine = true,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}

@Composable
fun DescriptionSection(
    description: String,
    onChanged: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = description,
        onValueChange = onChanged,
        label = { Text(text = "Description") },
        colors = colors,
        shape = shape,
        singleLine = true,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}

@Composable
fun DateSection(
    day: String,
    month: String,
    year: String,
    dayChanged: (String) -> Unit,
    monthChanged: (String) -> Unit,
    yearChanged: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = day,
            onValueChange = dayChanged,
            label = { Text(text = "Jour") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = month,
            onValueChange = monthChanged,
            label = { Text(text = "Mois") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = year,
            onValueChange = yearChanged,
            label = { Text(text = "Année") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LocationSection(
    location: String,
    onValueChange: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier
) {
    OutlinedTextField(
        value = location,
        onValueChange = { onValueChange(it) },
        label = { Text(text = "Lieu") },
        colors = colors,
        shape = shape,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(0.9f)
    )
}

@Composable
fun GuestSection(
    guestAdded: String,
    guestList: List<String>,
    onGuestAddedChange: (String) -> Unit,
    onAddGuest: () -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = guestAdded,
                onValueChange = onGuestAddedChange,
                label = { Text(text = "Ajouter un invité") },
                colors = colors,
                shape = shape,
                singleLine = true,
                modifier = Modifier.weight(2f)
            )

            Button(
                onClick = {
                    if (guestAdded.isNotBlank()) {
                        onAddGuest()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RoyalGold,
                    contentColor = TextBlack
                ),
                shape = shape,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Add",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(shape)
                .background(SurfaceCard)
                .padding(horizontal = 12.dp)
        ) {
            items(guestList) { guest ->
                Text(
                    text = guest,
                    color = TextBlack,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                HorizontalDivider(color = SubtleGrey)
            }
        }
    }
}

@Composable
fun AddButton(
    title: String,
    description: String,
    date: LocalDate,
    location: String,
    icon: ImageVector,
    createEvent: (Event) -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            val newEvent = Event(toDoEvents.count(), title, description, date, location, icon, false)
            createEvent(newEvent)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = VibrantCoral,
            contentColor = SurfaceCard
        ),
        shape = shape,
        modifier = modifier
    ) {
        Text(
            text = "Create Task",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
