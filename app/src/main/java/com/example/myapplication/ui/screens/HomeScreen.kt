package com.example.myapplication.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.local.Note
import com.example.myapplication.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: NoteViewModel,
    navController: NavController
) {

    val notes by viewModel.filteredNotes.collectAsState(initial = emptyList())
    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }
    var searchText by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Notes App")
                }
            )
        },

        snackbarHost = {
            SnackbarHost(snackbarHostState)
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

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    if (title.isNotEmpty() && description.isNotEmpty()) {

                        viewModel.insertNote(
                            Note(
                                title = title,
                                description = description
                            )
                        )

                        title = ""
                        description = ""

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Note Added"
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Save Note")
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = searchText,
                onValueChange = {

                    searchText = it

                    viewModel.updateSearchQuery(it)
                },
                label = {
                    Text("Search Notes")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {

                items(notes) { note ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {

                                navController.navigate(
                                    "detail/${note.id}/${note.title}/${note.description}"
                                )
                            },

                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = note.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Row {

                                IconButton(
                                    onClick = {

                                        navController.navigate(
                                            "edit/${note.id}/${note.title}/${note.description}"
                                        )
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit"
                                    )
                                }

                                IconButton(
                                    onClick = {

                                        viewModel.deleteNote(note)

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Note Deleted"
                                            )
                                        }
                                    }
                                ) {

                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}