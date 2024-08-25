package com.gulfrealestates.realestate.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gulfrealestates.realestate.Adapter.PagerAdapter.FavoritePagerAdapter
import com.gulfrealestates.realestate.databinding.FragmentFavoritesBinding
import com.google.android.material.tabs.TabLayout

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritePagerAdapter: FavoritePagerAdapter

    private lateinit var mContext: Context



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        setPagerAdapter()

        return binding.root
    }
    private fun setPagerAdapter() {

        favoritePagerAdapter = FavoritePagerAdapter(childFragmentManager)
        binding.vpFavorite.adapter = favoritePagerAdapter

        binding.vpFavorite.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tlTeam))
        binding.tlTeam.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                binding.vpFavorite.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }



}
