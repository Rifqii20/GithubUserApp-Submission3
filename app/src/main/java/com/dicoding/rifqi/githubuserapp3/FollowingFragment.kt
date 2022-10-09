package com.dicoding.rifqi.githubuserapp3

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.rifqi.githubuserapp3.databinding.FragmentFollowBinding
import com.dicoding.rifqi.githubuserapp3.detail.DetailActivity

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private var bind : FragmentFollowBinding? = null
    private val binding get() = bind!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentFollowBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailActivity.EX_USERNAME).toString()

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                rvUsers.layoutManager = GridLayoutManager(activity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(activity)
            }
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
        load(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it!=null){
                adapter.setList(it)
                load(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    private fun load(state: Boolean){
        if (state){
            binding.loading.visibility = View.VISIBLE
        }else {
            binding.loading.visibility = View.GONE
        }
    }
}