package com.test.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.data.base.model.ResponseModel
import com.test.data.jokes.models.mapped.Joke

const val JOKES_ENTITY = "jokes"

@Entity(tableName = JOKES_ENTITY)
data class JokeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "joke")
    val joke: String?
) : ResponseModel() {
    override fun map(): Joke {
        return Joke(id, joke.orEmpty())
    }
}