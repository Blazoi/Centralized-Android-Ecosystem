package com.example.todo.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.ToDoTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                MainUI()
            }
        }
    }
}

val TAG: String = "DEBUGGING"

data class Event(
    var id: Int,
    var title: String,
    var description: String,
    var date: LocalDate,
    var location: String,
    var icon: ImageVector,
    var isCompleted: Boolean
)

enum class Screen {
    TaskList, TaskInfo, CreateTask, TaskCalendar
}

public var allEvents = listOf<Event>()
public var toDoEvents = listOf<Event>(
    Event(
        0,
        "New Task",
        "Task Description Of DOom because of how long this desccription is",
        LocalDate.now(),
        "Nun ur bzn",
        Icons.Default.Menu,
        false
    )
)
public var eventsDone = listOf<Event>()

@Composable
fun MainUI() {
    var eventsList by remember { mutableStateOf(toDoEvents) }
    var topBarTitle by remember { mutableStateOf("Welcome Back, Jack") }
    var screen by remember { mutableStateOf(Screen.TaskList) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SurfaceCard,
        topBar = { TopBar(topBarTitle) },
        bottomBar = {
            BottomBar(
                { screen = Screen.TaskList },
                { screen = Screen.CreateTask },
                { screen = Screen.TaskCalendar },
            )
        }) { innerPadding ->

        when (screen) {
            Screen.TaskList -> TaskList(
                Modifier.padding(innerPadding),
                eventsList,
                { topBarTitle = it },
                { screen = Screen.TaskInfo })

            Screen.TaskInfo -> {}

            Screen.CreateTask -> CreateTask(
                Modifier
                    .padding(innerPadding)
                    .padding(top = 20.dp), {
                toDoEvents += it
                eventsList = toDoEvents
                Log.d(TAG, eventsList.toString())
                screen = Screen.TaskList
            }, { topBarTitle = it })

            Screen.TaskCalendar -> TaskCalendar(
                Modifier.padding(innerPadding), { topBarTitle = it })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title, fontWeight = FontWeight.Bold
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceCard, titleContentColor = Color.Black
        )
    )
}

@Composable
fun BottomBar(taskList: () -> Unit, createTask: () -> Unit, taskCalendar: () -> Unit) {
    BottomAppBar(
        containerColor = Color(0xFFB3C5BD),
        contentPadding = PaddingValues(0.dp),
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(3 / 4f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { taskList() }) {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "All active Tasks"
                    )
                }
                IconButton(onClick = { createTask() }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "Create a new Task"
                    )
                }
                IconButton(onClick = { taskCalendar() }) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "View all Tasks in a Calendar"
                    )
                }
            }
        }
    }
}
