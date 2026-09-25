package com.example.todo.screens.CreateTask.CreateTaskWidgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun DescriptionSection(
    description: String,
    onChanged: (String) -> Unit,
    colors: TextFieldColors,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = description,
        onValueChange = onChanged,
        label = { Text(text = "Description") },
        placeholder = { Text(text = "Description...") },
        colors = colors,
        shape = shape,
        singleLine = true,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}