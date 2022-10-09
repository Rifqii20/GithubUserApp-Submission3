package com.dicoding.rifqi.githubuserapp3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "githubUser")
class UserEntity(

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "avatar")
    val avatar_url: String,

    @field:ColumnInfo(name = "link")
    val html_url: String
)