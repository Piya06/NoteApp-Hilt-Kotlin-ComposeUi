package com.noteapp.hiltdagger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.noteapp.hiltdagger.data.Note
import com.noteapp.hiltdagger.data.NoteRepository
import com.noteapp.hiltdagger.util.AnalyticsTracker
import com.noteapp.hiltdagger.util.NoteIdGenerator
import com.noteapp.hiltdagger.validation.NotEmptyValidator
import com.noteapp.hiltdagger.validation.NoteValidator
import dagger.Lazy
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Provider


class ExpensiveLogger @Inject constructor(){
    fun log(message: String){
        println("[ExpensiveLogger] $message")
    }
}

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val loading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val saveSuccessMessage: String? = null
)


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,//// 1. Database operations (get, add, delete notes)
    private val validators: Set<@JvmSuppressWildcards NoteValidator>,// 2. Multibinding: Injects all validator rules
    // (e.g. NotEmptyValidator, MaxLengthValidator) as a Set
    private val idGenerator: NoteIdGenerator, // 3. Regular dependency: Used directly to generate IDs
    private val expensiveLoggerLazy: Lazy<ExpensiveLogger>, // 4. Lazy injection: Delays building
    // ExpensiveLogger until the first time it is actually needed
    private val idGeneratorProvider: Provider<NoteIdGenerator>
) : ViewModel(){
    private val _uiState = MutableStateFlow(NoteUiState())//internal state that only viewmodel can modify
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()//public read only state that compose ui can listen

    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { list ->
                _uiState.update { currentState ->
                    currentState.copy(
                        notes = list,
                        loading = false
                    )
                }
            }
        }
    }

    fun saveNote(title: String, content: String){
        val isValid = validators.all { it.validate(title) }
        if (!isValid){
            _uiState.update { it.copy(errorMessage = "Validation failed: Title is invalid") }
            return
        }

        _uiState.update { it.copy(isSaving = true, errorMessage = null) }

        expensiveLoggerLazy.get().log("Saving note draft=${idGenerator.generate()}")

        viewModelScope.launch {
            repository.addNote(title,content)
            _uiState.update {
                it.copy(isSaving = false, saveSuccessMessage = "Saved Successfully")
            }
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun peekNextDraftID(): String = idGeneratorProvider.get().generate()

    fun onMessageShown(){
        _uiState.update { it.copy(errorMessage = null, saveSuccessMessage = null) }
    }

}
















