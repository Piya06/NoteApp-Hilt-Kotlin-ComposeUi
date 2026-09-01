package com.noteapp.hiltdagger.di

import com.noteapp.hiltdagger.data.NoteRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NoteRepositoryEntryPoint {
    fun noteRepository(): NoteRepository
}