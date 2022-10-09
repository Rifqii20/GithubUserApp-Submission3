package com.dicoding.rifqi.githubuserapp3.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.rifqi.githubuserapp3.ListUserAdapter
import com.dicoding.rifqi.githubuserapp3.R
import com.dicoding.rifqi.githubuserapp3.User
import com.dicoding.rifqi.githubuserapp3.data.UserEntity
import com.dicoding.rifqi.githubuserapp3.databinding.ActivityFavoriteBinding
import com.dicoding.rifqi.githubuserapp3.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.title = "Favorite User"

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EX_USERNAME, data.login)
                    it.putExtra(DetailActivity.EX_ID, data.id)
                    it.putExtra(DetailActivity.AVATAR, data.avatar_url)
                    it.putExtra(DetailActivity.LINK, data.html_url)
                    startActivity(it)
                }
                Toast.makeText(applicationContext, data.login, Toast.LENGTH_SHORT).show()
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvUsers.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }
            rvUsers.adapter = adapter
        }

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null){
                val list = List(it)
                adapter.setList(list)
            }
        })
    }

    private fun List(users: List<UserEntity>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userData = User(
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userData)
        }
        return listUsers
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null){
                val list = List(it)
                adapter.setList(list)
            }
        })
    }
}