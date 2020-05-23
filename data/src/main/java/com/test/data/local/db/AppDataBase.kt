package com.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.db.dao.JokesDao
import com.test.data.local.db.dao.UserJokesDao
import com.test.data.local.db.entities.JokeEntity
import com.test.data.local.db.entities.UserJokeEntity

@Database(entities = [JokeEntity::class, UserJokeEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getJokesDao(): JokesDao
    abstract fun getUserJokesDao(): UserJokesDao
}