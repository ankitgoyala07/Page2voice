package com.example.page2voice.model

data class DocumentCard(
    val title: String,
    val subtitle: String = "",
    val progress: Float,
    val timeLeft: String,
    val isNowPlaying: Boolean = false
)