package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun DateSection(
    day: String,
    month: String,
    year: String,
    dayChanged: (String) -> Unit,
    monthChanged: (String) -> Unit,
    yearChanged: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = day,
            onValueChange = dayChanged,
            label = { Text(text = "Jour") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = month,
            onValueChange = monthChanged,
            label = { Text(text = "Mois") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = year,
            onValueChange = yearChanged,
            label = { Text(text = "Année") },
            colors = colors,
            shape = shape,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}