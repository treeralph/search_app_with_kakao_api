package com.example.camp_challenge_kakao_api.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.camp_challenge_kakao_api.URGENT_TAG
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.databinding.ItemBookmarkBinding

class BookmarkRecyclerViewAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.BookmarkViewHolder>() {

    private val itemList = mutableListOf<Bookmark>()
    private var bookmarkOnClickListener: BookmarkOnClickListener? = null

    init {
        if(fragment is BookmarkOnClickListener) {
            bookmarkOnClickListener = fragment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val currentItem = itemList[position]
        with(holder.binding) {
            // imageView.load(current.imageUrl)
            Glide.with(fragment).load(currentItem.imageUrl).into(imageView)
            removeButton.setOnClickListener {
                bookmarkOnClickListener?.bookmarkOnClick(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
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

interface BookmarkOnClickListener {
    fun bookmarkOnClick(bookmark: Bookmark)
}
