package com.example.notesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, name: String, text: String){

    var noteName = remember { mutableStateOf(name) }
    var noteText = remember { mutableStateOf(text) }
    Column {

        Button(
            onClick = {
                val index = NoteManager.getNoteIndex(name)
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
            label = { Text("Edit Note Text") }
        )
        Button(
            onClick = {
                val updatedName = noteName.value
                val updatedText = noteText.value
                val isValid = NoteManager.validate(updatedName, updatedText)

                if((updatedName != name || updatedText != text) && isValid) {
                    val index = NoteManager.getNoteIndex(name)
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