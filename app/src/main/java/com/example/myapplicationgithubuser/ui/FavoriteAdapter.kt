package com.example.myapplicationgithubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.myapplicationgithubuser.data.local.entity.Fav
import com.example.myapplicationgithubuser.databinding.ItemUserBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {
    private val listFav = ArrayList<Fav>()
    fun setListFav(listFav: List<Fav>) {
        val diffCallback = NoteDiffCallback(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFav.clear()
        this.listFav.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    override fun getItemCount(): Int {
        return listFav.size
    }

    inner class FavViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: Fav) {
            binding.tvItem.text = "${fav.username}\n"
            Glide.with(binding.root.context)
                .load(fav.avatarUrl) // URL Gambar
                .into(binding.imgItemUser)

            itemView.setOnClickListener {

                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra("user_login", fav.username)
                intentDetail.putExtra("user_id", fav.id)
                intentDetail.putExtra("user_avatar", fav.avatarUrl)
                itemView.context.startActivity(intentDetail)
            }
        }
    }
}