package com.dicoding.rifqi.githubuserapp3.response

import com.dicoding.rifqi.githubuserapp3.User
import com.google.gson.annotations.SerializedName

data class UsersResponse(
	@SerializedName("items")
	val items: List<User?>? = null
)
data class User (
	val login: String,
	val id: Int,
	val avatar_url: String,
	val html_url: String
)
