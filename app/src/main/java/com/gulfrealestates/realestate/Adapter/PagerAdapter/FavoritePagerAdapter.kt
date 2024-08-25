package com.gulfrealestates.realestate.Adapter.PagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.Fragments.Favorites.MyFavoriteFragment
import com.example.Fragments.Favorites.MyPropertiesFragment

class FavoritePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {

        if (position==0){

            return MyFavoriteFragment()

        }else if (position==1){

            return  MyPropertiesFragment()

        }

        return MyFavoriteFragment()


    }


}