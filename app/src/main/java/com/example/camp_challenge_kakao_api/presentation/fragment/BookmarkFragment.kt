package com.example.camp_challenge_kakao_api.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.camp_challenge_kakao_api.adapter.BookmarkOnClickListener
import com.example.camp_challenge_kakao_api.adapter.BookmarkRecyclerViewAdapter
import com.example.camp_challenge_kakao_api.data.model.Bookmark
import com.example.camp_challenge_kakao_api.databinding.FragmentBookmarkBinding
import com.example.camp_challenge_kakao_api.viewmodel.MainViewModel
import com.example.camp_challenge_kakao_api.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment(), BookmarkOnClickListener {

    companion object {
        const val TAG = "Bookmark"
    }

    private val binding by lazy { FragmentBookmarkBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(requireContext())
    }
    private val adapter by lazy { BookmarkRecyclerViewAdapter(this) } // click listener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(activity, 2)
            recyclerview.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.bookmarks.collect {
                adapter.itemsUpdate(it.bookmarks)
            }
        }
    }

    override fun bookmarkOnClick(bookmark: Bookmark) {
        viewModel.setBookmark(bookmark)
    }
}
