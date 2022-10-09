package com.dicoding.rifqi.githubuserapp3.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favEntity: UserEntity)

    @Query("Select * FROM GITHUBUSER ORDER BY login ASC")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Query("Select count(*) FROM githubUser WHERE GITHUBUSER.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM GITHUBUSER WHERE GITHUBUSER.id = :id")
    fun removeFromFavorite(id: Int): Int
}