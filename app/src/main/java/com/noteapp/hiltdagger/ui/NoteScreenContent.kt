package com.noteapp.hiltdagger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noteapp.hiltdagger.data.Note
import com.noteapp.hiltdagger.ui.theme.NoteAppHiltDaggerTheme
import org.w3c.dom.Text

@Composable
fun NoteScreenContent(
    state: NoteUiState,
    onSaveNote: (title: String, content: String) -> Unit,
    onDeleteNote: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.fillMaxSize().padding(16.dp)){

        AddNoteForm(onSaveNote = onSaveNote, isSaving = state.isSaving)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Box(modifier = Modifier.fillMaxSize()){
            when {
                state.loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.notes.isEmpty() -> Text(
                    text = "No notes yet - add one above.",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> NoteList(notes = state.notes, onDeleteNote = onDeleteNote)
            }
        }
    }
}

@Composable
private fun AddNoteForm(
    onSaveNote: (title: String, content: String) -> Unit,
    isSaving: Boolean
){
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column{

        OutlinedTextField(
            value = title,
            onValueChange = {title = it},
            label = { Text("Title")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content,
            onValueChange = {content = it},
            label = {Text("Content")},
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Button(
            onClick = {
                onSaveNote(title,content)
                title = ""
                content = ""
            },
            enabled = !isSaving,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (isSaving) "Saving..." else "Save note")
        }

    }

}


@Composable
private fun NoteList(notes: List<Note>, onDeleteNote: (Note) -> Unit){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items = notes, key = {it.id}){ notes ->
            NoteListItem(note = notes, onDelete = {onDeleteNote(notes)})
        }
    }
}

@Composable
private fun NoteListItem(note: Note, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Spacing between card items only
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp) // Single padding container for internal content
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f) // Takes available space without pushing layout
                )


              //  CompositionLocalProvider(
                //    LocalMinimumInteractiveComponentSize provides 0.dp
              //  ) {
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note"
                        )
                    }
               // }

            }

            if (note.content.isNotBlank()){
                Spacer(modifier = Modifier.  height(4.dp))

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))
            }


        }
    }
}





@Preview(showBackground = true)
@Composable
private fun NoteScreenContent_WithNotes_Preview() {

    NoteAppHiltDaggerTheme {
        NoteScreenContent(
            state = NoteUiState(
                notes = listOf(
                    Note(id = 1, title = "Groceries", content = "Milk, eggs, bread"),
                    Note(id = 2, title = "Interview Preparation", content = "Review Dependency Injection")
                ),
                loading = false
            ),
            onSaveNote = {_, _ -> },
            onDeleteNote = {}
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun NoteScreenContent_Empty_Preview(){
    NoteAppHiltDaggerTheme {
        NoteScreenContent(
            state = NoteUiState(notes = emptyList(), loading = false),
            onSaveNote = { _, _ -> },
            onDeleteNote = {}
        )
    }
}