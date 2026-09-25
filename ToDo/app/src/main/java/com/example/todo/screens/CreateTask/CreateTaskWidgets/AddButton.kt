package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todo.screens.Event
import com.example.todo.screens.allEvents
import com.example.todo.screens.toDoEvents
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.VibrantCoral
import java.time.LocalDate

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
            val newEvent =
                Event(
                    allEvents.count(),
                    title,
                    description,
                    date,
                    location,
                    icon,
                    false
                )
            createEvent(newEvent)
        }, colors = ButtonDefaults.buttonColors(
            containerColor = VibrantCoral, contentColor = SurfaceCard
        ), shape = shape, modifier = modifier
    ) {
        Text(
            text = "Create Task", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
    }
}