package com.noteapp.hiltdagger.util

import dagger.hilt.android.scopes.ViewModelScoped
import java.util.UUID
import javax.inject.Inject

@ViewModelScoped
class NoteIdGenerator @Inject constructor() {
    fun generate(): String = UUID.randomUUID().toString()
}