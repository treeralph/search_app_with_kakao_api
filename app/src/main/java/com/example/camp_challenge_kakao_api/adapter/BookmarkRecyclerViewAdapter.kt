package com.example.camp_challenge_kakao_api.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.camp_challenge_kakao_api.URGENT_TAG
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.databinding.ItemBookmarkBinding
import com.example.camp_challenge_kakao_api.databinding.ItemSearchBinding

class BookmarkRecyclerViewAdapter(

) : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.BookmarkViewHolder>() {

    private val itemList = mutableListOf<Bookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val current = itemList[position]
        with(holder.binding) {
            imageView.load(current.imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun itemsUpdate(bookmarks: List<Bookmark>) {
        Log.e(URGENT_TAG, "itemsUpdate: called")
        itemList.clear()
        itemList.addAll(bookmarks)
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(
        val binding: ItemBookmarkBinding,
    ) : ViewHolder(binding.root)
}