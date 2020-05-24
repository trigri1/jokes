package com.test.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.test.data.local.db.entities.USER_JOKES_ENTITY
import com.test.data.local.db.entities.UserJokeEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserJokesDao {

    @Query("SELECT * FROM $USER_JOKES_ENTITY")
    fun getAllUserJokes(): Observable<List<UserJokeEntity>>

    @Query("SELECT * FROM $USER_JOKES_ENTITY ORDER BY RANDOM() LIMIT 1")
    fun getRandomJoke(): Single<UserJokeEntity>

    @Insert(onConflict = REPLACE)
    fun insert(jokeEntity: UserJokeEntity): Completable

    @Delete
    fun delete(jokeEntity: UserJokeEntity): Completable

    @Query("DELETE FROM $USER_JOKES_ENTITY WHERE liked_id == :likedId")
    fun deleteByLikedId(likedId: Long): Completable

    @Query("DELETE FROM $USER_JOKES_ENTITY WHERE id == :id")
    fun deleteById(id: Long): Completable
}