package com.gulfrealestates.realestate.Adapter.PagerAdapter

import com.gulfrealestates.realestate.Fragments.HomeFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gulfrealestates.realestate.Fragments.FavoritesFragment
import com.gulfrealestates.realestate.Fragments.ProfileFragment
import com.gulfrealestates.realestate.Fragments.PropertiesFragment

class MainPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {

        if (position==0){

            return HomeFragment()

        }else if (position==1){

            return  PropertiesFragment()

        }else if (position==2){

            return  FavoritesFragment()

        }else if (position==3){

            return ProfileFragment()
        }

        return HomeFragment()


    }


}