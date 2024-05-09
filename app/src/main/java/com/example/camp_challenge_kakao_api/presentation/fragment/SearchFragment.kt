package com.example.camp_challenge_kakao_api.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.camp_challenge_kakao_api.MORE_SEARCH
import com.example.camp_challenge_kakao_api.adapter.DocumentOnClickListener
import com.example.camp_challenge_kakao_api.adapter.ItemRecyclerViewAdapter
import com.example.camp_challenge_kakao_api.databinding.FragmentSearchBinding
import com.example.camp_challenge_kakao_api.data.model.Document
import com.example.camp_challenge_kakao_api.viewmodel.MainViewModel
import com.example.camp_challenge_kakao_api.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), DocumentOnClickListener {

    companion object {
        const val TAG = "Search"
    }

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(requireContext())
    }
    private val adapter by lazy { ItemRecyclerViewAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            button.setOnClickListener(buttonOnClickListener)
            floatingActionButton.setOnClickListener {
                recyclerView.scrollToPosition(0)
            }

            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(
                object: RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if(!recyclerView.canScrollVertically(1)
                            && newState == RecyclerView.SCROLL_STATE_IDLE) {
                            Log.e(TAG, "onScrollStateChanged: end arrived")
                            viewModel.search(flag = MORE_SEARCH)
                        }
                    }
                }
            )
        }

        lifecycleScope.launch {
            viewModel.documents.collect {
                adapter.itemsUpdate(it)
            }
        }

        lifecycleScope.launch {
            viewModel.document.collect {
                adapter.itemUpdate(it)
            }
        }
    }

    private val buttonOnClickListener: (View) -> Unit = {
        viewModel.search(query = binding.editText.text.toString())
    }

    override fun documentOnClick(document: Document) {
        viewModel.setDocument(document)
    }
}
