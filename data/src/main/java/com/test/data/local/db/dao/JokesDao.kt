package com.test.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.test.data.local.db.entities.JOKES_ENTITY
import com.test.data.local.db.entities.JokeEntity


@Dao
interface JokesDao {

    @Query("SELECT * FROM $JOKES_ENTITY")
    fun getAllJokes(): List<JokeEntity>
    
    @Insert(onConflict = REPLACE)
    fun insert(jokeEntity: JokeEntity)

    @Delete
    fun delete(jokeEntity: JokeEntity)
}