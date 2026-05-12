package com.example.myapplication.repository

import com.example.myapplication.data.local.Note
import com.example.myapplication.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
class NoteRepository(
    private val dao: NoteDao
) {

    val allNotes = dao.getAllNotes()
    fun searchNotes(query: String): Flow<List<Note>> {
        return dao.searchNotes(query)
    }

    suspend fun insert(note: Note) {
        dao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        dao.deleteNote(note)
    }
}