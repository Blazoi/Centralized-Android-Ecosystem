package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todo.data.Task
import com.example.todo.data.TaskViewModel
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.VibrantCoral
import java.time.LocalDate

@Composable
fun AddButton(
    taskViewModel: TaskViewModel,
    title: String,
    description: String,
    date: LocalDate,
    location: String,
    icon: ImageVector,
    guestList: List<String>,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    val tasks = taskViewModel.tasks.collectAsState().value
    Button(
        onClick = {
            val newTask =
                Task(
                    title,
                    description,
                    date.toString(),
                    location,
                    //icon,
                    guestList.toString(),
                    false
                )
            taskViewModel.add(newTask)
        }, colors = ButtonDefaults.buttonColors(
            containerColor = VibrantCoral, contentColor = SurfaceCard
        ), shape = shape, modifier = modifier
    ) {
        Text(
            text = "Create Task", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
    }
}