package com.example.todo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: TaskDao = AppDataBase.getDatabase(application.applicationContext).TaskDao()

    val tasks: StateFlow<List<Task>> = dao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun add(task: Task) = viewModelScope.launch(Dispatchers.IO) { dao.insert(task) }
    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) { dao.update(task) }
    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) { dao.delete(task) }
    fun getTasksByDate(date: String) = viewModelScope.launch(Dispatchers.IO) { dao.getTasksByDate(date) }
}