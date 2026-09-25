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
import com.example.todo.screens.CreateTask.CreateTask
import com.example.todo.screens.TaskList.TaskList
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.ui.theme.primaryDark
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
    var date: LocalDate?,
    var location: String,
    var icon: ImageVector,
    var isCompleted: Boolean
)

enum class Screen {
    TaskList, TaskInfo, CreateTask, TaskCalendar
}
//public var datedEvents = mutableMapOf<LocalDate, MutableList<Event>>()
public var datedEvents = mutableMapOf<LocalDate?, MutableList<Event>>()
public var allEvents = listOf<Event>()
public var eventsDone = listOf<Event>()
public var toDoEvents = listOf<Event>(
//    Event(
//        0,
//        "New Task",
//        "Desciption of the event",
//        LocalDate.now(),
//        "Nun ur bzn",
//        Icons.Default.Menu,
//        false
//    )
)

@Composable
fun MainUI() {
    var eventsList by remember { mutableStateOf(toDoEvents) }
    var topBarTitle by remember { mutableStateOf("Welcome Back, Jack") }
    var screen by remember { mutableStateOf(Screen.CreateTask) }

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
                    .padding(top = 20.dp),
                {
                    // Update data

                    AddEvent(it)
                    // Update UI
                    eventsList = toDoEvents
                    screen = Screen.TaskList
                },
                { topBarTitle = it })

            Screen.TaskCalendar -> TaskCalendar(
                Modifier.padding(innerPadding), { topBarTitle = it })
        }
    }
}

fun AddEvent(newEvent: Event) {
    // Probably gon have to change this,
    // doesn't seem very efficient
    allEvents += newEvent
    toDoEvents = allEvents.filter{event -> !event.isCompleted }


    if (newEvent.date != null) {
        val date = newEvent.date
        datedEvents.getOrPut(date) { mutableListOf() }.add(0, newEvent)
        Log.d(TAG, datedEvents.toString())
    }
}