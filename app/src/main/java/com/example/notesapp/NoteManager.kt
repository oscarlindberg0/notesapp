package com.example.notesapp

import androidx.compose.runtime.mutableStateListOf

//ChatGPT helped me come up with the following solution to be able to save data when navigating
//between screens: https://chat.openai.com/c/6d9b5ceb-b81a-45b2-a69d-a938b95a1b94
object NoteManager{
    val globalNoteList = mutableStateListOf<Note>()

    fun getNoteIndex(name: String): Int {
        return globalNoteList.indexOfFirst { it.name == name }
    }

    fun validate(name: String, text: String): Boolean {
        if(name.length < 3 ||
            name.length > 50 ||
            text.length > 120)
            return false
        globalNoteList.forEach { note: Note ->
            if(note.name == name)
                return false
        }
        return true
    }
}