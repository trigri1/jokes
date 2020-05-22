package com.test.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val JOKES_ENTITY = "jokes"

@Entity(tableName = JOKES_ENTITY)
data class JokeEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long?,

    @ColumnInfo(name = "joke")
    val joke: String?,

    @ColumnInfo(name = "liked")
    val likes: Boolean?
)