package com.example.notesapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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