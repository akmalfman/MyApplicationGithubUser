package com.example.myapplicationgithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplicationgithubuser.R
import com.example.myapplicationgithubuser.data.local.entity.Fav
import com.example.myapplicationgithubuser.data.remote.response.DetailUserResponse
import com.example.myapplicationgithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = intent.getStringExtra("user_login").toString()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Follower"
            } else {
                tab.text = "Following"
            }
        }.attach()

        supportActionBar?.hide()


        val userLogin = intent.getStringExtra("user_login")
        val id = intent.getIntExtra("user_id", 0)
        val avatarUrl = intent.getStringExtra("user_avatar")

        Log.d("sat", "onCreate: $userLogin")
        if (userLogin != null) {
            detailViewModel.findDetailUser(userLogin)
            detailViewModel.findFollowers(userLogin)
        }

        detailViewModel.listDetailUser.observe(this) { listDetailUser ->
            setDetailUserData(listDetailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        factory = ViewModelFactory.getInstance(application)

        var isFavorited = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteViewModel.checkFav(id)
            if (count != null) {
                if (count > 0) {
                    binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_fill_24)
                    isFavorited = true
                } else {
                    binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
                    isFavorited = false
                }
            }
        }

        binding.favoriteButton.setOnClickListener {
            val entity = userLogin?.let { Fav(id, userLogin, avatarUrl.toString()) }
            isFavorited = !isFavorited
            if (isFavorited) {
                binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_fill_24)
                favoriteViewModel.insert(entity as Fav)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
                favoriteViewModel.deleteUser(id)
            }
        }
    }

    private fun setDetailUserData(listDetailUser: DetailUserResponse) {
        binding.tvName.text = listDetailUser.name
        binding.tvLogin.text = listDetailUser.login
        Glide.with(this@DetailActivity)
            .load("${listDetailUser.avatarUrl}?v=4")
            .into(binding.imgItemUser)
        binding.tvFollowers.text = listDetailUser.followers.toString()
        binding.tvFollowing.text = listDetailUser.following.toString()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}