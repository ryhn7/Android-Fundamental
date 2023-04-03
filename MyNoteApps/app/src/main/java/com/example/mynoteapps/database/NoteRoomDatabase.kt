package com.example.mynoteapps.database

import android.content.Context
import androidx.room.*

@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database"
                    )
                        .build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }
    }
}