package com.example.camp_challenge_kakao_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.databinding.ItemBookmarkBinding
import com.example.camp_challenge_kakao_api.databinding.ItemSearchBinding
import com.example.week_use_kakao_api.data.model.Document
import okhttp3.internal.notifyAll

class BookmarkItemRecyclerViewAdapter(

): RecyclerView.Adapter<BookmarkItemRecyclerViewAdapter.BookmarkViewHolder>() {

    private val itemList = mutableListOf<Bookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val currentItem = itemList[position]
        with(holder.binding) {
            imageView.load(currentItem.imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun itemRemove(bookmark: Bookmark) {
        val index = itemList.indexOf(bookmark)
        itemList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemAdd(bookmark: Bookmark) {
        itemList.add(bookmark)
        notifyItemInserted(itemList.size - 1)
    }

    fun itemUpdate(bookmarks: List<Bookmark>) {
        itemList.clear()
        itemList.addAll(bookmarks)
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(
        val binding: ItemBookmarkBinding
    ): RecyclerView.ViewHolder(binding.root)
}