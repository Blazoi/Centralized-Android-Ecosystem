package com.example.todo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.ToDoTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjuster
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

data class Event(val title: String, val date: String, val icon: String)

var eventsList = listOf(
    Event("Go Running", "Today", "Run"),
    Event("Birthday", "Tomorrow", "Event"),
    Event("Go To Class", "Jan 1st 2027", "location"),
    Event("Take Online Class", "Jan 1st 2027", "location"),
    Event("Take Motorcycle Exam", "Jan 1st 2027", "location"),
    Event("Ride Motorcycle", "Jan 1st 2027", "location"),
    Event("Pay for gas", "Jan 1st 2027", "location"),
    Event("Finish my lab", "Jan 1st 2027", "location"),
)

@Composable
fun MainUI() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() },
        bottomBar = { BottomBar() }) { innerPadding ->
        MainPage(
            Modifier
                .padding(innerPadding)
        )
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
fun BottomBar() {
    BottomAppBar() {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(3 / 4f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun MainPage(mod: Modifier) {
    Column(
        modifier = mod
            .fillMaxSize()
            .background(Color(245, 245, 253)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuickAccessCalendar()
        MainToDoList()
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
        "past" to arrayOf(Color(223, 224, 246), Color(146, 146, 205), Color.White, Color(149, 149, 149)),
        "present" to arrayOf(Color(110, 110, 244), Color.White, Color(223, 224, 246), Color(146, 146, 205)),
        "future" to arrayOf(Color(223, 224, 246), Color(146, 146, 205), Color.White, Color.Black),
        "weekend" to arrayOf(Color(215, 119, 107), Color.White, Color.White, Color(209, 142, 137)),
        "weekendPresent" to arrayOf(Color(215, 119, 107), Color.White, Color(253, 234, 232), Color(200, 93, 80)),
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(1 / 5f)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Week of Monday ${weekOf.dayOfMonth}${suffix[weekOf.dayOfMonth.toString().last()] ?: "th"}",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in 0..6) {
                val day = weekOf.plusDays(i.toLong())
                val isDayWeekEnd = day.dayOfWeek.toString().uppercase() == "SATURDAY" || day.dayOfWeek.toString().uppercase() == "SUNDAY"
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
fun DateSticker(date: LocalDate, mod: Modifier, colorWave: Array<Color>) {
    Column(
        modifier = mod
            .clip(RoundedCornerShape(5.dp)),
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
fun MainToDoList() {
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
fun ToDoItem(title: String, date: String, icon: String) {
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
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(3f)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = date,
                fontSize = 15.sp
            )
        }

        IconButton(onClick = {
            Toast.makeText(context, LocalDate.now().plusDays(4).dayOfWeek.toString(), Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                imageVector = getIcon(icon),
                contentDescription = ""
            )
        }
    }
}