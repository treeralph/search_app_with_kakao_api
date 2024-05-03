package com.example.camp_challenge_kakao_api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.camp_challenge_kakao_api.R
import com.example.camp_challenge_kakao_api.databinding.ItemSearchBinding
import com.example.week_use_kakao_api.data.model.Document

class SearchItemRecyclerViewAdapter(): RecyclerView.Adapter<SearchItemRecyclerViewAdapter.SearchViewHolder>() {

    private val itemList = mutableListOf<Document>()
    private var bookmarkButtonClickListener: ItemClickListener? = null

    constructor(fragment: Fragment): this() {
        if(fragment is ItemClickListener) {
            bookmarkButtonClickListener = fragment
        } else throw Exception("bookmark button click listener: null")
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
            bookmarkImageView.setImageResource(
                if(currentItem.bookmarked) R.drawable.ic_bookmark
                else R.drawable.ic_bookmark_blank
            )
            bookmarkImageView.setOnClickListener{
                bookmarkButtonClickListener?.itemOnClick(currentItem)

            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun itemEdit(document: Document) {
        val index = itemList.indexOf(document)
        itemList[index] = document
        notifyItemChanged(index)
    }

    fun itemUpdate(documents: List<Document>) {
        itemList.clear()
        itemList.addAll(documents)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(
        val binding: ItemSearchBinding
    ): RecyclerView.ViewHolder(binding.root)
}

interface ItemClickListener {
    fun itemOnClick(document: Document)
}