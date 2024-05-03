package com.example.camp_challenge_kakao_api.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.camp_challenge_kakao_api.adapter.BookmarkItemRecyclerViewAdapter
import com.example.camp_challenge_kakao_api.adapter.SearchItemRecyclerViewAdapter
import com.example.camp_challenge_kakao_api.databinding.FragmentBookmarkBinding
import com.example.week_use_kakao_api.viewmodel.MainViewModel
import com.example.week_use_kakao_api.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 보관한 이미지는 DB관련 라이브러리 사용 금지.
 * SharedPreferences 사용 권장
 *      이지만 옛날 문서 같으니 data store 사용해도 좋을 듯 하다.
 *
 * */

class BookmarkFragment : Fragment() {

    companion object {
        const val TAG = "Bookmark"
    }

    private val binding by lazy { FragmentBookmarkBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookmarkItemRecyclerViewAdapter()

        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(activity, 2)
            recyclerview.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.bookmarks.collectLatest {
                adapter.itemUpdate(it.bookmarkList)
            }
        }
    }
}