package com.noteapp.hiltdagger.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun addNote(title: String, content: String): Long
    suspend fun deleteNote(note: Note)
}