package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// ✅ Data Model
data class Note(
    val title: String,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesAppUI()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppUI() {

    // 🔹 State
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val notes = remember { mutableStateListOf<Note>() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes App") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 🔹 Title Input
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Description Input
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Button
            Button(
                onClick = {
                    if (title.isEmpty() || description.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Fill all fields!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        notes.add(Note(title, description))
                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Note")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Notes List
            LazyColumn {
                items(notes) { note ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "Clicked: ${note.title}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}