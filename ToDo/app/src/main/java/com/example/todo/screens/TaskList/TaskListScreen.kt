package com.example.todo.screens.TaskList

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
import androidx.compose.foundation.lazy.items
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
import com.example.todo.data.TaskViewModel
import com.example.todo.ui.theme.CanvasBackground
import com.example.todo.ui.theme.DarkViolet
import com.example.todo.ui.theme.RoyalGold
import com.example.todo.ui.theme.SoftLavender
import com.example.todo.ui.theme.SubtleGrey
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.TextBlack
import com.example.todo.ui.theme.VibrantCoral
import com.example.todo.ui.theme.primaryDark
import com.example.todo.ui.theme.primaryLight
import kotlinx.coroutines.flow.count
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import androidx.compose.runtime.collectAsState
import com.example.todo.data.Task

@Composable
fun TaskList(
    taskViewModel: TaskViewModel,
    modifier: Modifier,
    topBarTitle: (String) -> Unit,
    moreInfo: (Int) -> Unit
) {
    var tasksList by remember { mutableStateOf<List<Task>>(emptyList()) }


    topBarTitle("Welcome Back, Jack")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceCard),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuickAccessCalendar(
            taskViewModel,
            { tasksList = it },
            Modifier.weight(1f)
        )
        MainToDoList(
            taskViewModel,
            tasksList,
            Modifier.weight(3f),
            moreInfo = { moreInfo(it) }
        )
    }
}

@Composable
fun MainToDoList(
    taskViewModel: TaskViewModel,
    tasksList: List<Task>,
    modifier: Modifier,
    moreInfo: (Int) -> Unit
) {
    val tasks = taskViewModel.tasks.collectAsState().value
    val areEvents = tasks.isNotEmpty()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(primaryDark)
            .padding(10.dp),
        verticalArrangement = if (areEvents) Arrangement.spacedBy(10.dp) else Arrangement.Center
    ) {
        if (areEvents) {
            items(tasks) { task ->
                ToDoItem(
                    task.id,
                    task.title,
                    task.description,
                    LocalDate.parse(task.date),
                    task.location,
//                    task.icon,
                    task.isCompleted,
                    {
                        taskViewModel.update(
                            Task(
                                task.title,
                                task.description,
                                task.date,
                                task.location,
                                task.guests,
                                !task.isCompleted,
                                task.id
                            )
                        )
                    },
                    { moreInfo(task.id) })
            }
        } else {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks for now...",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        color = primaryLight
                    )
                }
            }
        }
    }
}