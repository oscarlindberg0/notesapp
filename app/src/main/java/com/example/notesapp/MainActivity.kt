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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.compose.navigate
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

data class Note(val name: String, val text: String)

//ChatGPT helped me come up with the following solution to be able to save data when navigating
//between screens: https://chat.openai.com/c/6d9b5ceb-b81a-45b2-a69d-a938b95a1b94
object NoteManager{
    val globalNoteList = mutableStateListOf<Note>()

    fun GetNoteIndex(name: String): Int {
        return globalNoteList.indexOfFirst { it.name == name }
    }

    fun validate(name: String, text: String): Boolean {
        if(name.length < 3 ||
            name.length > 50 ||
            text.length > 120)
            return false
        return true
    }
}

//I learned how to switch screens from here: https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3
@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "StartScreen") {
        composable("StartScreen"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val text = backStackEntry.arguments?.getString("text") ?: ""
            StartScreen(navController = navController, name = name, text = text)
        }
        composable(
            route = "EditNoteScreen?name={name}&text={text}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("text") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val text = backStackEntry.arguments?.getString("text") ?: ""
            EditNoteScreen(navController = navController, name = name, text = text)
        }
    }
}

//When I made the following function i got help from https://developer.android.com/jetpack/compose/tutorial
//and ChatGPT: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController, name: String, text: String) {
    val notesState = NoteManager.globalNoteList
    val newNoteName = remember { mutableStateOf("") }
    val newNoteText = remember { mutableStateOf("") }

    Column {
        AddNoteButton() {
            val noteName = newNoteName.value
            val noteText = newNoteText.value
            val isValid = NoteManager.validate(noteName, noteText)
            if (noteName.isNotEmpty() && isValid) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, name: String, text: String){

    var noteName = remember { mutableStateOf(name) }
    var noteText = remember { mutableStateOf(text) }
    Column {

        Button(
            onClick = {
                val index = NoteManager.GetNoteIndex(name)
                NoteManager.globalNoteList.removeAt(index)
                navController.navigate("StartScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
        ) {
            Text(text = "Delete Note")

        }

        OutlinedTextField(
            value = noteName.value,
            onValueChange = { noteName.value = it },
            label = { Text("Edit Note Name") }

        )
        OutlinedTextField(
            value = noteText.value,
            onValueChange = { noteText.value = it },
            label = { Text("Edit Note Text")}
        )
        Button(
            onClick = {
                val updatedName = noteName.value
                val updatedText = noteText.value
                val isValid = NoteManager.validate(updatedName, updatedText)

                if((updatedName != name || updatedText != text) && isValid) {
                    val index = NoteManager.GetNoteIndex(name)
                    NoteManager.globalNoteList.removeAt(index)
                    NoteManager.globalNoteList.add(Note(updatedName, updatedText))
                }
                navController.navigate("StartScreen?name=$updatedName&text=$updatedText")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
        ) {
            Text(text = "Save")

        }
        Button(
            onClick = {
                navController.navigate("StartScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
        ) {
            Text(text = "Cancel")

        }
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
        onClick = { navController.navigate("EditNoteScreen?name=$name&text=$text") },
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








