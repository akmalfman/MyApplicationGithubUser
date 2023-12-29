package com.example.myapplicationgithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationgithubuser.data.local.entity.Fav
import com.example.myapplicationgithubuser.databinding.ActivityFavBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }



    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        adapter = FavoriteAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFav.addItemDecoration(itemDecoration)

        factory = ViewModelFactory.getInstance(application)
        favoriteViewModel.getFavoritedUser().observe(this) { userItems ->
            setUserData(userItems)

        }

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserData(userItems: List<Fav>) {
        val adapter = FavoriteAdapter()
        adapter.setListFav(userItems)
        binding.rvFav.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}