package com.example.notesapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController) {
    val notesState = NoteManager.globalNoteList
    val newNoteName = remember { mutableStateOf("") }
    val newNoteText = remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            //I got the scrollable column from here: https://androidwave.com/create-a-scrollable-column-in-jetpack-compose/
            .verticalScroll(rememberScrollState())
    ){
        //I had ChatGPT explain how to handle button clicks: https://chat.openai.com/c/5b4da3b5-0596-4c01-a965-9f1ec9eb1842
        AddNoteButton {
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
                    label = { Text("New Note Name") },
                    supportingText = { if(!NoteManager.validate(newNoteName.value, "aaaa")){
                        Text(text = "Invalid input",
                            style = androidx.compose.ui.text.TextStyle(
                                color = Color.Red
                            )
                        )
                    }
                    }

                )
                OutlinedTextField(
                    value = newNoteText.value,
                    onValueChange = { newNoteText.value = it },
                    label = { Text("New Note Text") },
                    supportingText = { if(!NoteManager.validate("aaaa", newNoteText.value)){
                        Text(text = "Invalid input",
                            style = androidx.compose.ui.text.TextStyle(
                                color = Color.Red
                            )
                        )
                    }
                    }
                )
            }

        }
        NoteList(notes = notesState, navController)

    }
}