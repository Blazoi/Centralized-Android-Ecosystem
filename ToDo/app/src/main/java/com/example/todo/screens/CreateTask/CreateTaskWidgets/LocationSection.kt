package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun LocationSection(
    location: String,
    onValueChange: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier
) {
    OutlinedTextField(
        value = location,
        onValueChange = { onValueChange(it) },
        label = { Text(text = "Lieu") },
        placeholder = { Text(text = "123, Rue De L'est... Zoom...") },
        colors = colors,
        shape = shape,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(0.9f)
    )
}