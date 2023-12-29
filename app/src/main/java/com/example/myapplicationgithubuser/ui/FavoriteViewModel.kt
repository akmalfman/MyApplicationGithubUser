package com.example.myapplicationgithubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationgithubuser.data.local.FavRepository
import com.example.myapplicationgithubuser.data.local.entity.Fav

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavRepository: FavRepository = FavRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoritedUser(): LiveData<List<Fav>> {
        _isLoading.value = false
        return mFavRepository.getFavoritedUser()
    }

    fun insert(fav: Fav) {
        mFavRepository.insert(fav)
    }

    fun deleteUser(id: Int) {
        mFavRepository.delete(id)
    }

    fun checkFav(id: Int) = mFavRepository.checkFav(id)

}