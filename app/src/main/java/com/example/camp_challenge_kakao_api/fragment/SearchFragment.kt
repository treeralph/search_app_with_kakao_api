package com.example.camp_challenge_kakao_api.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.camp_challenge_kakao_api.adapter.ItemOnClickListener
import com.example.camp_challenge_kakao_api.adapter.ItemRecyclerViewAdapter
import com.example.camp_challenge_kakao_api.databinding.FragmentSearchBinding
import com.example.week_use_kakao_api.data.model.Document
import com.example.week_use_kakao_api.viewmodel.MainViewModel
import com.example.week_use_kakao_api.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), ItemOnClickListener {

    companion object {
        const val TAG = "Search"
    }

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(requireContext()) } // SharedViewModel 사용 권장
    private val adapter by lazy { ItemRecyclerViewAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            button.setOnClickListener(buttonOnClickListener)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
            recyclerView.adapter = adapter
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
        viewModel.search(binding.editText.text.toString())
    }

    override fun itemOnClick(document: Document) {
        viewModel.setDocument(document)
    }
}
