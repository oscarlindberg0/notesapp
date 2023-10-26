package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.*

import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.compose.navigate
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

data class Note(val name: String, val text: String)

//I learned how to switch screens from here: https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3
@Composable
fun EditNoteScreen(navController: NavController){
    Text("Edit note screen")
}

@Composable
fun NextScreen() {
    Text("This is the next screen")
}

@Composable
fun App() {
    val navController = rememberNavController()

    // Set up the navigation graph
    NavHost(navController = navController, startDestination = "StartScreen") {
        composable("StartScreen") {
            StartScreen(navController = navController)
        }
        composable("EditNoteScreen"){
            EditNoteScreen(navController = navController)
        }
        composable("NextScreen") {
            NextScreen()
        }
    }
}

//When I made the following function i got help from https://developer.android.com/jetpack/compose/tutorial
//and ChatGPT: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController) {
    val notesState = remember { mutableStateListOf<Note>() }
    val newNoteName = remember { mutableStateOf("") }
    val newNoteText = remember { mutableStateOf("") }

    Column {
        AddNoteButton() {
            val noteName = newNoteName.value
            val noteText = newNoteText.value
            if (noteName.isNotEmpty()) {
                notesState.add(Note(noteName, noteText))
                newNoteName.value = ""
                newNoteText.value = ""
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                //I learned about this text field from: https://www.composables.com/components/material3/outlinedtextfield
                OutlinedTextField(
                    value = newNoteName.value,
                    onValueChange = { newNoteName.value = it },
                    label = { Text("New Note Name") }

                )
                OutlinedTextField(
                    value = newNoteText.value,
                    onValueChange = { newNoteText.value = it },
                    label = { Text("New Note Text")}
                )
            }

        }
        NoteList(notes = notesState, navController)

    }
}

//I had ChatGPT explain how to handle button clicks: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
@Composable
fun AddNoteButton(onAddNoteButtonClick: () -> Unit) {
    Button(
        onClick = { onAddNoteButtonClick() },
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
fun AddNote(name: String, text: String, navController: NavController){
    Button(
        onClick = { navController.navigate("EditNoteScreen") },
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

    }
}


@Composable
fun NoteList(notes: List<Note>, navController: NavController) {
    Column {
        notes.forEach{ note: Note ->
            AddNote(note.name, note.text, navController)
        }
    }

}






