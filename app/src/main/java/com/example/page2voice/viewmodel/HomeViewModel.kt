package com.example.page2voice.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.page2voice.model.DocumentCard

class HomeViewModel : ViewModel() {

    private val _documents = mutableStateListOf(
        DocumentCard(
            title = "Operating System Notes",
            subtitle = "Chapter 4: Memory Management",
            progress = 0.68f,
            timeLeft = "18 min left",
            isNowPlaying = true
        ),
        DocumentCard(
            title = "Database Assignment",
            subtitle = "Normalization",
            progress = 0.42f,
            timeLeft = "34 min left"
        ),
        DocumentCard(
            title = "Final Report.pdf",
            subtitle = "Project Submission",
            progress = 0.15f,
            timeLeft = "51 min left"
        )
    )

    val documents: List<DocumentCard> get() = _documents

    fun togglePlayPause(document: DocumentCard) {
        val index = _documents.indexOfFirst { it.title == document.title }
        if (index != -1) {
            val wasPlaying = _documents[index].isNowPlaying
            // Pause all others if playing a new document
            for (i in _documents.indices) {
                _documents[i] = _documents[i].copy(isNowPlaying = false)
            }
            _documents[index] = _documents[index].copy(isNowPlaying = !wasPlaying)
        }
    }
}
