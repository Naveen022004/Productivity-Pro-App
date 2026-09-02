package com.naveen.productivitypro

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naveen.productivitypro.data.Task
import com.naveen.productivitypro.notifications.ReminderScheduler
import com.naveen.productivitypro.ui.theme.ProductivityProAppTheme
import com.naveen.productivitypro.viewmodel.ProductivityViewModel
import java.text.DateFormat
import java.util.Calendar

class MainActivity : ComponentActivity() {
    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ProductivityProAppTheme { ProductivityApp() } }
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductivityApp(vm: ProductivityViewModel = viewModel()) {
    val tasks by vm.tasks.collectAsStateWithLifecycle()
    var showAddTask by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Productivity Pro") }) },
        floatingActionButton = { FloatingActionButton(onClick = { showAddTask = true }) { Text("+") } }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            Spacer(Modifier.height(16.dp))
            val completed = tasks.count { it.completed }
            Text("Today", style = MaterialTheme.typography.headlineMedium)
            Text("$completed of ${tasks.size} tasks completed")
            Spacer(Modifier.height(16.dp))
            if (tasks.isEmpty()) {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(24.dp)) {
                        Text("No tasks yet", style = MaterialTheme.typography.titleLarge)
                        Text("Tap + to create your first task and schedule a reminder.")
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(tasks, key = { it.id }) { task ->
                        TaskCard(task,
                            onToggle = {
                                vm.toggleTask(task)
                                if (task.completed) ReminderScheduler.schedule(context, task)
                                else ReminderScheduler.cancel(context, task.id)
                            },
                            onDelete = {
                                ReminderScheduler.cancel(context, task.id)
                                vm.deleteTask(task)
                            })
                    }
                }
            }
        }
    }

    if (showAddTask) AddTaskDialog(
        onDismiss = { showAddTask = false },
        onSave = { title, notes, dueAt ->
            vm.addTask(title, notes, dueAt) { id ->
                dueAt?.let { ReminderScheduler.schedule(context, Task(id = id, title = title, notes = notes, dueAtMillis = it)) }
            }
            showAddTask = false
        }
    )
}

@Composable
private fun TaskCard(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = task.completed, onCheckedChange = { onToggle() })
            Column(Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None)
                if (task.notes.isNotBlank()) Text(task.notes, style = MaterialTheme.typography.bodySmall)
                task.dueAtMillis?.let { Text("Due: ${DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(it)}") }
            }
            TextButton(onClick = onDelete) { Text("Delete") }
        }
    }
}

@Composable
private fun AddTaskDialog(onDismiss: () -> Unit, onSave: (String, String, Long?) -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var dueAt by remember { mutableLongStateOf(0L) }
    var hasDueDate by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    fun chooseDateTime() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                dueAt = Calendar.getInstance().apply {
                    set(year, month, day, hour, minute, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                hasDueDate = true
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(title, { title = it; error = false }, label = { Text("Task title") }, singleLine = true, isError = error,
                    supportingText = { if (error) Text("Enter a task title") })
                OutlinedTextField(notes, { notes = it }, label = { Text("Notes (optional)") }, minLines = 2)
                OutlinedButton(onClick = ::chooseDateTime) { Text(if (hasDueDate) "Change reminder" else "Add reminder") }
                if (hasDueDate) Text(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(dueAt))
            }
        },
        confirmButton = { Button(onClick = {
            if (title.isBlank()) error = true else onSave(title.trim(), notes.trim(), if (hasDueDate) dueAt else null)
        }) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
