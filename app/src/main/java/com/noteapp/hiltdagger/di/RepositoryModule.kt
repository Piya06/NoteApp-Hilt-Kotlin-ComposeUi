package com.noteapp.hiltdagger.di

import com.noteapp.hiltdagger.data.NoteDao
import com.noteapp.hiltdagger.data.NoteRepository
import com.noteapp.hiltdagger.data.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        dao: NoteDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NoteRepository = NoteRepositoryImpl(dao, ioDispatcher)
}