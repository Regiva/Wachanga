package com.example.wachanga.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Long? = null,
    val content: String,
    val done: Boolean,
)
