package com.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.db.dao.JokesDao
import com.test.data.local.db.entities.JokeEntity

@Database(entities = [JokeEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getJokesDao(): JokesDao
}