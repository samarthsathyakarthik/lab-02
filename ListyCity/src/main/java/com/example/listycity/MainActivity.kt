package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    private val repository = CityRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        repository = repository,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CityListScreen(repository: CityRepository, modifier: Modifier = Modifier) {
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var newCityText by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { showAddDialog = true }) {
                Text("ADD CITY")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    selectedCity?.let { repository.deleteCity(it) }
                    selectedCity = null
                },
                enabled = selectedCity != null
            ) {
                Text("DELETE CITY")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(repository.getCities()) { city ->
                val isSelected = city == selectedCity
                Text(
                    text = city,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCity = if (isSelected) null else city }
                        .background(if (isSelected) Color.LightGray else Color.Transparent)
                        .padding(12.dp)
                )
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                newCityText = ""
            },
            title = { Text("Add City") },
            text = {
                OutlinedTextField(
                    value = newCityText,
                    onValueChange = { newCityText = it },
                    label = { Text("City name") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    repository.addCity(newCityText)
                    newCityText = ""
                    showAddDialog = false
                }) {
                    Text("CONFIRM")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    newCityText = ""
                    showAddDialog = false
                }) {
                    Text("CANCEL")
                }
            }
        )
    }
}