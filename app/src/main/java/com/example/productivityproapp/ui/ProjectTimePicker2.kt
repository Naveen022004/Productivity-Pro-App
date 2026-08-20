package com.example.productivityproapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productivityproapp.ui.theme.ProductivityProAppTheme
import java.util.Calendar
import java.util.Locale

class ProjectTimePicker2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductivityProAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TimePickerGreeting(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerGreeting(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("No time selected") }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false
    )

    Column(modifier = modifier.padding(24.dp)) {
        Text("Selected Time: $selectedTime")
        Spacer(Modifier.height(20.dp))
        TextButton(onClick = { showDialog = true }) { Text("Select AM/PM time") }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    val amPm = if (hour < 12) "AM" else "PM"
                    val hour12 = if (hour % 12 == 0) 12 else hour % 12
                    selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hour12, minute, amPm)
                    showDialog = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } },
            text = { TimePicker(state = timePickerState) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerGreetingPreview() {
    ProductivityProAppTheme { TimePickerGreeting() }
}
