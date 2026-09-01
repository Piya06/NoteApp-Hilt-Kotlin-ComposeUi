package com.noteapp.hiltdagger.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher)
    : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun getNoteById(id: Int): Note? = withContext(ioDispatcher){
        noteDao.getNoteById(id)
    }

    override suspend fun addNote(title: String, content: String): Long = withContext(ioDispatcher) {
        noteDao.insertNote(Note(title = title, content = content))
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}