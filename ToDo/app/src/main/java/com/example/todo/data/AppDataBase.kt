package com.example.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.time.Instant

@Database(
    entities = [Task::class],
    version = 6,
    exportSchema = false
)


abstract class AppDataBase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase =
            Instance ?:synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "task_database"
                ).fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
    }
}