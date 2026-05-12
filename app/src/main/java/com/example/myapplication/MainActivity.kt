package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.local.NoteDatabase
import com.example.myapplication.repository.NoteRepository
import com.example.myapplication.ui.screens.DetailScreen
import com.example.myapplication.ui.screens.EditNoteScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.NoteViewModel
import com.example.myapplication.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())
        val factory = NoteViewModelFactory(repository)

        setContent {

            MyApplicationTheme {

                val viewModel: NoteViewModel = viewModel(
                    factory = factory
                )

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    composable("home") {

                        HomeScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    composable(
                        "detail/{id}/{title}/{description}"
                    ) { backStackEntry ->

                        val id =
                            backStackEntry.arguments
                                ?.getString("id")
                                ?.toInt() ?: 0

                        val title =
                            backStackEntry.arguments
                                ?.getString("title") ?: ""

                        val description =
                            backStackEntry.arguments
                                ?.getString("description") ?: ""

                        DetailScreen(
                            id = id,
                            title = title,
                            description = description,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable(
                        "edit/{id}/{title}/{description}"
                    ) { backStackEntry ->

                        val id =
                            backStackEntry.arguments
                                ?.getString("id")
                                ?.toInt() ?: 0

                        val title =
                            backStackEntry.arguments
                                ?.getString("title") ?: ""

                        val description =
                            backStackEntry.arguments
                                ?.getString("description") ?: ""

                        EditNoteScreen(
                            id = id,
                            oldTitle = title,
                            oldDescription = description,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}