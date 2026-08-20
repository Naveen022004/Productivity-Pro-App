package com.example.productivityproapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productivityproapp.ui.theme.ProductivityProAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductivityProAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductivityHome(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductivityHome(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Productivity Pro",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Stay focused. Get more done.",
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        Button(onClick = { }) {
            Text("Get Started")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductivityHomePreview() {
    ProductivityProAppTheme {
        ProductivityHome()
    }
}
