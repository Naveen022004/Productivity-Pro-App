package com.example.productivityproapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.productivityproapp.ui.theme.PRODUCTIVITYPROappTheme

private data class Task(
    val id: Int,
    val title: String,
    val category: String,
    val priority: String,
    val completed: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PRODUCTIVITYPROappTheme {
                ProductivityApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@androidx.compose.runtime.Composable
private fun ProductivityApp() {
    val tasks = remember {
        mutableStateListOf(
            Task(1, "Plan today's priorities", "Planning", "High"),
            Task(2, "Complete project documentation", "Study", "Medium")
        )
    }
    var query by remember { mutableStateOf("") }
    var showAdd by remember { mutableStateOf(false) }

    val filtered = tasks.filter { it.title.contains(query, ignoreCase = true) }
    val completed = tasks.count { it.completed }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Productivity Pro") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAdd = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                Text("Your productivity dashboard", style = MaterialTheme.typography.headlineSmall)
                Text("$completed of ${tasks.size} tasks completed", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Search tasks") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
            }
            items(filtered, key = { it.id }) { task ->
                TaskCard(
                    task = task,
                    onComplete = {
                        val index = tasks.indexOfFirst { it.id == task.id }
                        if (index >= 0) tasks[index] = task.copy(completed = !task.completed)
                    },
                    onDelete = { tasks.remove(task) }
                )
            }
        }
    }

    if (showAdd) {
        AddTaskDialog(
            onDismiss = { showAdd = false },
            onAdd = { title, category, priority ->
                val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                tasks.add(Task(nextId, title, category, priority))
                showAdd = false
            }
        )
    }
}

@androidx.compose.runtime.Composable
private fun TaskCard(task: Task, onComplete: () -> Unit, onDelete: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text("${task.category} • ${task.priority}", style = MaterialTheme.typography.bodyMedium)
                if (task.completed) Text("Completed", color = MaterialTheme.colorScheme.primary)
            }
            Row {
                IconButton(onClick = onComplete) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Complete task")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete task")
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun AddTaskDialog(onDismiss: () -> Unit, onAdd: (String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var priority by remember { mutableStateOf("Medium") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(title, { title = it }, label = { Text("Task title") }, singleLine = true)
                OutlinedTextField(category, { category = it }, label = { Text("Category") }, singleLine = true)
                OutlinedTextField(priority, { priority = it }, label = { Text("Priority: Low / Medium / High") }, singleLine = true)
            }
        },
        confirmButton = {
            Button(enabled = title.isNotBlank(), onClick = { onAdd(title.trim(), category.trim(), priority.trim()) }) { Text("Add") }
        },
        dismissButton = { Button(onClick = onDismiss) { Text("Cancel") } }
    )
}
