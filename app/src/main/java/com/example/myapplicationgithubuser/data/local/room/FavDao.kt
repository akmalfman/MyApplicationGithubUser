package com.example.myapplicationgithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplicationgithubuser.data.local.entity.Fav

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: Fav)

    @Query("DELETE FROM fav WHERE fav.id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM fav")
    fun getAllFav(): LiveData<List<Fav>>

    @Query("SELECT EXISTS(SELECT * FROM fav WHERE fav.id = :id)")
    fun isUserFavorited(id: Int): Int
}