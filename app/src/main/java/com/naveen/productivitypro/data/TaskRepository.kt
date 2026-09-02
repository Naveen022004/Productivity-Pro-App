package com.naveen.productivitypro.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val tasks: Flow<List<Task>> = dao.observeTasks()

    suspend fun add(title: String, notes: String, dueAtMillis: Long?): Long =
        dao.insert(Task(title = title.trim(), notes = notes.trim(), dueAtMillis = dueAtMillis))

    suspend fun toggle(task: Task) = dao.update(task.copy(completed = !task.completed))
    suspend fun delete(task: Task) = dao.delete(task)
    suspend fun pendingReminders(): List<Task> = dao.getPendingWithReminders()
}
