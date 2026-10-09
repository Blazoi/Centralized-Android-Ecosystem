package com.example.todo.data

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")data class Task (
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val guests: String,
    val isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)