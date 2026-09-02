package com.naveen.productivitypro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naveen.productivitypro.data.AppDatabase
import com.naveen.productivitypro.data.Task
import com.naveen.productivitypro.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(AppDatabase.get(application).taskDao())

    val tasks: StateFlow<List<Task>> = repository.tasks.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )

    fun addTask(title: String, notes: String, dueAtMillis: Long?, onSaved: (Long) -> Unit = {}) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val id = repository.add(title, notes, dueAtMillis)
            onSaved(id)
        }
    }

    fun toggleTask(task: Task) = viewModelScope.launch { repository.toggle(task) }
    fun deleteTask(task: Task) = viewModelScope.launch { repository.delete(task) }
    suspend fun pendingReminders(): List<Task> = repository.pendingReminders()
}
