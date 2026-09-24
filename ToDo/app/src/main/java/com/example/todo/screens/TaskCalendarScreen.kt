package com.example.todo.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskCalendar(modifier: Modifier, topBarTitle: (String) -> Unit) {
    topBarTitle("All of your tasks")
}