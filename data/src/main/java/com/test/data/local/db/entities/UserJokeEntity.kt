package com.test.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.data.base.model.ResponseModel
import com.test.data.jokes.models.mapped.Joke

const val USER_JOKES_ENTITY = "user_jokes"

@Entity(tableName = USER_JOKES_ENTITY)
data class UserJokeEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "liked_id")
    val likedId: Long = -1,

    @ColumnInfo(name = "joke")
    val joke: String?
) : ResponseModel() {
    override fun map(): Joke {
        return Joke(likedId, joke.orEmpty())
    }
}