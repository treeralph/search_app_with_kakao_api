package com.example.camp_challenge_kakao_api.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.camp_challenge_kakao_api.databinding.ItemSearchBinding
import com.example.week_use_kakao_api.data.model.Document
import okhttp3.internal.notifyAll

class ItemRecyclerViewAdapter(

): RecyclerView.Adapter<ItemRecyclerViewAdapter.SearchViewHolder>() {

    private val itemList = mutableListOf<Document>()

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
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
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