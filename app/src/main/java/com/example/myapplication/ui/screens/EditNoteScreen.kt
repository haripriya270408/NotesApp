package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.local.Note
import com.example.myapplication.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    id: Int,
    oldTitle: String,
    oldDescription: String,
    viewModel: NoteViewModel,
    navController: NavController
) {

    var title by remember {
        mutableStateOf(oldTitle)
    }

    var description by remember {
        mutableStateOf(oldDescription)
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Edit Note")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text("Title")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                label = {
                    Text("Description")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    val updatedNote = Note(
                        id = id,
                        title = title,
                        description = description
                    )

                    viewModel.updateNote(updatedNote)

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Update Note")
            }
        }
    }
}