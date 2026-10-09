package com.example.todo.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.todo.data.Task
import com.example.todo.data.TaskViewModel
import com.example.todo.screens.CreateTask.CreateTask
import com.example.todo.screens.TaskList.TaskList
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.ui.theme.primaryDark
import java.time.LocalDate
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                MainUI(taskViewModel)
            }
        }
    }
}

val TAG: String = "DEBUGGING"

enum class Screen {
    TaskList, TaskInfo, CreateTask, TaskCalendar
}

@Composable
fun MainUI(taskViewModel: TaskViewModel) {
    var topBarTitle by remember { mutableStateOf("Welcome Back, Jack") }
    var eventsList by remember { mutableStateOf(taskViewModel.tasks) }
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
                taskViewModel,
                Modifier.padding(innerPadding),
                { topBarTitle = it },
                { screen = Screen.TaskInfo })

            Screen.TaskInfo -> {}

            Screen.CreateTask -> CreateTask(
                taskViewModel,
                Modifier
                    .padding(innerPadding)
                    .padding(top = 20.dp),
                { topBarTitle = it })

            Screen.TaskCalendar -> TaskCalendar(
                Modifier.padding(innerPadding), { topBarTitle = it })
        }
    }
}