package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Note
import com.example.myapplication.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val allNotes = repository.allNotes

    private val _searchQuery = MutableStateFlow("")

    val searchQuery = _searchQuery.asStateFlow()

    val filteredNotes = _searchQuery
        .flatMapLatest { query ->

            if (query.isEmpty()) {
                repository.allNotes
            } else {
                repository.searchNotes(query)
            }
        }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun insertNote(note: Note) {

        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun updateNote(note: Note) {

        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {

        viewModelScope.launch {
            repository.delete(note)
        }
    }
}

class NoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return NoteViewModel(repository) as T
    }
}