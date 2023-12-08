package com.example.roomdatabaseappexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val cellPhoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)




