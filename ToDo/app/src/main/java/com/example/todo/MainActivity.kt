package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.ToDoTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

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

data class Event(var title: String, var description: String, var date: LocalDate, var location: String, var icon: ImageVector)
enum class Screen {
    TaskList, CreateTask, TaskCalendar
}

@Composable
fun MainUI() {
    var eventsList by remember { mutableStateOf(listOf<Event>()) }

    var screen by remember { mutableStateOf(Screen.CreateTask) }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopBar() }, bottomBar = {
        BottomBar(
            { screen = Screen.TaskList },
            { screen = Screen.CreateTask },
            { screen = Screen.TaskCalendar },
        )
    }) { innerPadding ->

        when (screen) {
            Screen.TaskList -> TaskList(
                Modifier.padding(innerPadding),
                eventsList
            )

            Screen.CreateTask -> CreateTask(
                Modifier.padding(innerPadding),
                {
                    eventsList += it
                    screen = Screen.TaskList
                }
            )

            Screen.TaskCalendar -> TaskCalendar(Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Centralized Ecosystem") },
    )
}

@Composable
fun BottomBar(taskList: () -> Unit, createTask: () -> Unit, taskCalendar: () -> Unit) {
    BottomAppBar() {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(3 / 4f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { taskList() }) {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = ""
                    )
                }
                IconButton(onClick = { createTask() }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = ""
                    )
                }
                IconButton(onClick = { taskCalendar() }) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth, contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(modifier: Modifier, eventsList: List<Event>) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(245, 245, 253)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuickAccessCalendar()
        MainToDoList(eventsList)
    }
}

@Composable
fun QuickAccessCalendar() {

    val currentDate = LocalDate.now()
    val weekOf = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val suffix = mapOf(
        '1' to "st",
        '2' to "nd",
        '3' to "rd",
    )

    val colors = mapOf(
        "past" to arrayOf(
            Color(223, 224, 246), Color(146, 146, 205), Color.White, Color(149, 149, 149)
        ),
        "present" to arrayOf(
            Color(110, 110, 244), Color.White, Color(223, 224, 246), Color(146, 146, 205)
        ),
        "future" to arrayOf(Color(223, 224, 246), Color(146, 146, 205), Color.White, Color.Black),
        "weekend" to arrayOf(Color(215, 119, 107), Color.White, Color.White, Color(209, 142, 137)),
        "weekendPresent" to arrayOf(
            Color(215, 119, 107), Color.White, Color(253, 234, 232), Color(200, 93, 80)
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(1 / 5f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Week of Monday ${weekOf.dayOfMonth}${
                suffix[weekOf.dayOfMonth.toString().last()] ?: "th"
            }", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.Black
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in 0..6) {
                val day = weekOf.plusDays(i.toLong())
                val isDayWeekEnd =
                    day.dayOfWeek.toString().uppercase() == "SATURDAY" || day.dayOfWeek.toString()
                        .uppercase() == "SUNDAY"
                val isDayPast = day < currentDate
                val isDayFuture = day > currentDate
                val isDayToday = day == currentDate

                val colorWave = when {
                    isDayToday && !isDayWeekEnd -> colors.getValue("present")
                    isDayPast -> colors.getValue("past")
                    isDayFuture && !isDayWeekEnd -> colors.getValue("future")
                    isDayWeekEnd && isDayToday -> colors.getValue("weekendPresent")
                    else -> colors.getValue("weekend")
                }

                DateSticker(day, Modifier.weight(1f), colorWave)
            }
        }
    }
}

@Composable
fun DateSticker(date: LocalDate, modifier: Modifier, colorWave: Array<Color>) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
                .background(colorWave[0]),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfWeek.toString().substring(0, 3),
                fontSize = 15.sp,
                color = colorWave[1]
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorWave[2])
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorWave[3],
            )
        }
    }
}

@Composable
fun MainToDoList(eventsList: List<Event>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxHeight(5 / 6f)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (event in eventsList) {
            ToDoItem(event.title, event.date, event.icon)
        }
    }
}

@Composable
fun ToDoItem(title: String, date: LocalDate, icon: ImageVector) {
    fun getIcon(name: String): ImageVector {
        return when (name) {
            "location" -> Icons.Default.AddLocation
            "run" -> Icons.Default.RunCircle
            "event" -> Icons.Default.DateRange
            else -> Icons.Default.Home
        }
    }

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(5f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Blue)
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(3f)) {
            Text(
                text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            Text(
                text = date.toString(), fontSize = 15.sp
            )
        }

        IconButton(onClick = {
            Toast.makeText(
                context, LocalDate.now().plusDays(4).dayOfWeek.toString(), Toast.LENGTH_SHORT
            ).show()
        }) {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                imageVector = icon,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun CreateTask(modifier: Modifier, createEvent: (Event) -> Unit) {

    var title by remember { mutableStateOf("New Task") }
    var description by remember { mutableStateOf("Task Description") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var location by remember { mutableStateOf("Location X") }
    var guestAdded by remember { mutableStateOf("test") }
    var guestList by remember { mutableStateOf(emptyList<String>()) }
    var actionIcon by remember { mutableStateOf("New Task") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(8f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(text = "nom de la tâche") },
                modifier = Modifier
                    .fillMaxWidth(4 / 5f)
                    .height(75.dp)
            )
            // Desc
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth(4 / 5f)
                    .height(75.dp)
            )
            // Date
            Row(
                modifier = Modifier
                    .fillMaxWidth(4 / 5f)
            ) {
                Text(
                    text = date.toString(),
                    modifier = Modifier
                        .height(75.dp)
                )

            }
            //Location
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Lieu") },
                modifier = Modifier
                    .fillMaxWidth(4 / 5f)
                    .height(75.dp)
            )

            // Guests
            Column(
                modifier = Modifier
                    .fillMaxWidth(4 / 5f)
                    .height(75.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OutlinedTextField(
                        value = guestAdded,
                        onValueChange = { guestAdded = it },
                        label = { Text(text = "Ajouter un invité") },
                        modifier = Modifier.weight(2f)
                    )

                    Button(
                        onClick = {
                            guestList += guestAdded
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(5 / 6f)
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
                        .height(200.dp)
                ) {
                    items(guestList) { guest ->
                        Text(
                            text = guest,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        Button(
            onClick = {
                val newEvent = Event(title, description, date, location, Icons.Default.Map)
                createEvent(newEvent)

            },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Create Task")
        }
    }
}

@Composable
fun TaskCalendar(modifier: Modifier) {

}