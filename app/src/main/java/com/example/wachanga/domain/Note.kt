package com.example.wachanga.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val content: String,
    val done: Boolean,
)
