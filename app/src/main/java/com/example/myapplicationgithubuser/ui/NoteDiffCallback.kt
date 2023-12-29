package com.example.myapplicationgithubuser.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplicationgithubuser.data.local.entity.Fav

class NoteDiffCallback(private val oldFavList: List<Fav>, private val newFavList: List<Fav>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavList[oldItemPosition]
        val newNote = newFavList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatarUrl == newNote.avatarUrl
    }
}