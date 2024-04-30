package com.example.camp_challenge_kakao_api.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.camp_challenge_kakao_api.fragment.BookmarkFragment
import com.example.camp_challenge_kakao_api.fragment.SearchFragment

class MainViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SearchFragment()
            1 -> BookmarkFragment()
            else -> { throw Exception() }
        }
    }
}