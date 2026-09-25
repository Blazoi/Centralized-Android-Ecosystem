package com.example.todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.SurfaceCard
import com.example.todo.ui.theme.primaryDark

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
        containerColor = primaryDark,
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
