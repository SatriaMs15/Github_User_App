package com.example.movietvshowapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movietvshowapp.data.entity.ShowData

@Database(entities = [ShowData::class],
    version = 1,
    exportSchema = false)
abstract class ShowDatabase : RoomDatabase() {

    abstract fun showDao(): ShowDao

    companion object {

        @Volatile
        private var INSTANCE: ShowDatabase? = null

        fun getInstance(context: Context): ShowDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShowDatabase::class.java,
                    "ShowMovie.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}