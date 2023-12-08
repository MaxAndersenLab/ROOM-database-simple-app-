package com.example.roomdatabaseappexample.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdatabaseappexample.Contact

@Database(
   entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {

    abstract val dao: ContactDao


}