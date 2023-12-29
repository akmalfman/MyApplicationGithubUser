package com.example.myapplicationgithubuser.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplicationgithubuser.data.local.entity.Fav
import com.example.myapplicationgithubuser.data.local.room.FavDao
import com.example.myapplicationgithubuser.data.local.room.FavRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {

    private val mNFavDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDatabase.getDatabase(application)
        mNFavDao = db.favDao()
    }

    fun insert(fav: Fav) {
        executorService.execute { mNFavDao.insert(fav) }
    }

    fun delete(id: Int) {
        executorService.execute { mNFavDao.delete(id) }
    }

    fun getFavoritedUser(): LiveData<List<Fav>> {
        return mNFavDao.getAllFav()
    }

    fun checkFav(id: Int) = mNFavDao.isUserFavorited(id)
}