package com.dicoding.rifqi.githubuserapp3.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dicoding.rifqi.githubuserapp3.api.ApiConfig
import com.dicoding.rifqi.githubuserapp3.data.UserDao
import com.dicoding.rifqi.githubuserapp3.data.UserDatabase
import com.dicoding.rifqi.githubuserapp3.data.UserEntity
import com.dicoding.rifqi.githubuserapp3.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userFavDao: UserDao?
    private var userFavDb: UserDatabase?

    init {
        userFavDb = UserDatabase.getInstance(application)
        userFavDao = userFavDb?.userDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(uname: String, id: Int, avatar: String, link: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserEntity(
                uname,
                id,
                avatar,
                link
            )
            userFavDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userFavDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userFavDao?.removeFromFavorite(id)
        }
    }
}