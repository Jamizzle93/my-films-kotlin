package com.mysticwater.myfilms.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mysticwater.myfilms.data.Film

@Database(entities =  arrayOf(Film::class), version = 1)
abstract class FilmsDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao

    companion object {
        private var INSTANCE: FilmsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): FilmsDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            FilmsDatabase::class.java, "Films.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}