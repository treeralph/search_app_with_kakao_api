package com.example.camp_challenge_kakao_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.camp_challenge_kakao_api.R
import com.example.camp_challenge_kakao_api.databinding.ItemSearchBinding
import com.example.week_use_kakao_api.data.model.Document

class ItemRecyclerViewAdapter(
    fragment: Fragment
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.SearchViewHolder>() {

    private val itemList = mutableListOf<Document>()
    private var itemOnClickListener: ItemOnClickListener? = null

    init {
        if(fragment is ItemOnClickListener) {
            itemOnClickListener = fragment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = itemList[position]
        with(holder.binding) {
            imageView.load(currentItem.imageUrl) // Image URL
            titleTextView.text = currentItem.titleText
            timeTextView.text = currentItem.time.toString()
            bookmarkButtonImageView.setImageResource(
                if(currentItem.bookmarked) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark_blank
            )
            bookmarkButtonImageView.setOnClickListener {
                itemOnClickListener?.itemOnClick(currentItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    fun itemsUpdate(documents: List<Document>) {
        itemList.clear()
        itemList.addAll(documents)
        notifyDataSetChanged()
    }

    fun itemUpdate(document: Document) {
        val index = itemList.indexOfFirst {
            it.url == document.url
        }
        if(index == -1) {
            return
        }
        itemList[index] = document
        notifyItemChanged(index)
    }

    inner class SearchViewHolder(
        val binding: ItemSearchBinding
    ): RecyclerView.ViewHolder(binding.root)
}

interface ItemOnClickListener {
    fun itemOnClick(document: Document)
}