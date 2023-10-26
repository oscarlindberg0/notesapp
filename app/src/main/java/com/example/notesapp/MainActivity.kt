package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreen()
        }
    }
}

data class Note(val name: String)

//When I made the following function i got help from https://developer.android.com/jetpack/compose/tutorial
//and ChatGPT: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    val notesState = remember { mutableStateListOf<Note>() }
    val newNote = remember { mutableStateOf("") }

    Column {
        AddNoteButton() {
            val noteName = newNote.value
            if (noteName.isNotEmpty()) {
                notesState.add(Note(noteName))
                newNote.value = ""
            }
        }

        //I learned about this text field from: https://www.composables.com/components/material3/outlinedtextfield
        OutlinedTextField(
            value = newNote.value,
            onValueChange = { newNote.value = it },
            label = { Text("New Note Name") }

        )

        NoteList(notes = notesState)



    }
}

//I had ChatGPT explain how to handle button clicks: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
@Composable
fun AddNoteButton(onAddNoteClick: () -> Unit) {
    Button(
        onClick = { onAddNoteClick() },
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Add Note",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )}
}

@Composable
fun AddNote(text: String){
    Button(
        onClick = {  },
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )}
}


@Composable
fun NoteList(notes: List<Note>) {
    Column {
        notes.forEach{ note: Note ->
            AddNote(note.name)
        }
    }

}






