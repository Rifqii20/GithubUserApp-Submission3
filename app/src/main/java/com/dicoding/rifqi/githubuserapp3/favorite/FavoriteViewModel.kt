package com.dicoding.rifqi.githubuserapp3.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.rifqi.githubuserapp3.data.UserDao
import com.dicoding.rifqi.githubuserapp3.data.UserDatabase
import com.dicoding.rifqi.githubuserapp3.data.UserEntity

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userFavDao: UserDao?
    private var userFavDb: UserDatabase?

    init {
        userFavDb = UserDatabase.getInstance(application)
        userFavDao = userFavDb?.userDao()    }

    fun getFavoriteUser(): LiveData<List<UserEntity>>? {
        return userFavDao?.getFavoriteUser()
    }

}