package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddNoteButton()
        }
    }
}

//When I made the following function i got help from https://developer.android.com/jetpack/compose/tutorial
//and ChatGPT: https://chat.openai.com/c/9ab7f4c0-8f93-479d-9220-b90e2e744f15
@Composable
fun AddNoteButton() {
    Row(modifier = Modifier.padding(all = 8.dp)) {

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(120.dp)
            ) {
                Text(
                    text = "Add note",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
    }
}




