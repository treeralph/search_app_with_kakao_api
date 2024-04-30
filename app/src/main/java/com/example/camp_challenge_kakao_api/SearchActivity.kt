package com.example.camp_challenge_kakao_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.camp_challenge_kakao_api.adapter.MainViewPagerAdapter
import com.example.camp_challenge_kakao_api.databinding.ActivitySearchBinding
import com.example.camp_challenge_kakao_api.fragment.BookmarkFragment
import com.example.camp_challenge_kakao_api.fragment.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        with(binding) {
            viewPager.adapter = MainViewPagerAdapter(this@SearchActivity)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when(position) {
                    0 -> tab.text = SearchFragment.TAG
                    1 -> tab.text = BookmarkFragment.TAG
                }
            }.attach()
        }
    }
}