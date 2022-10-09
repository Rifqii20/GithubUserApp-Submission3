package com.dicoding.rifqi.githubuserapp3.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.rifqi.githubuserapp3.SectionsPagerAdapter
import com.dicoding.rifqi.githubuserapp3.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://github.com/${binding.txtUsername.text}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        val username = intent.getStringExtra(EX_USERNAME)
        val id = intent.getIntExtra(EX_ID, 0)
        val avatar = intent.getStringExtra(AVATAR)
        val link = intent.getStringExtra(LINK)
        val bundle = Bundle()
        bundle.putString(EX_USERNAME, username)

        viewModel = ViewModelProvider(this).get(
            DetailUserViewModel::class.java
        )
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    txtName.text = it.name; if (it.name == null) {
                    txtName.text = "-"
                }
                    txtUsername.text = it.login
                    txtRepo.text = "Repository : ${it.publicRepos}"
                    txtLoc.text = "Location : ${it.location}"; if (it.location == null) {
                    txtLoc.text = "Location : - "
                }
                    txtComp.text = "Company : ${it.company}"; if (it.company == null) {
                    txtComp.text = "Company : - "
                }
                    txtFollowers.text = " ${it.followers} Followers"
                    txtFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(imgUser)
                }
            }
        })

        var checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val i = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (i != null) {
                    if (i > 0) {
                        binding.btnFav.isChecked = true
                        checked = true
                    } else {
                        binding.btnFav.isChecked = false
                        checked = false
                    }
                }
            }
        }

        binding.btnFav.setOnClickListener {
            checked = !checked
            if (checked) {
                viewModel.addToFavorite(username.toString(), id, avatar.toString(), link.toString())
                Toast.makeText(this, username+ "_Added to Favorites", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFromFavorite(id)
                Toast.makeText(this, username+ "_removed from Favorites", Toast.LENGTH_SHORT).show()
            }
            binding.btnFav.isChecked = checked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tab.setupWithViewPager(viewPager)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EX_USERNAME = "ex_uname"
        const val EX_ID = "ex_id"
        const val AVATAR = "avatar"
        const val LINK = "link"
    }
}