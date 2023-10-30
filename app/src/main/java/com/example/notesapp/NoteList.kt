package com.example.notesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NoteList(notes: List<Note>, navController: NavController) {
    Column {
        notes.forEach{ note: Note ->
            AddNote(note.name, note.text, navController)
        }
    }
}