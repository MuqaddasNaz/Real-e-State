package com.gulfrealestates.realestate.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.gulfrealestates.realestate.Activities.Main.CommercialActivity
import com.gulfrealestates.realestate.Activities.Main.ResidentialActivity
import com.gulfrealestates.realestate.databinding.FragmentPropertiesBinding

class PropertiesFragment : Fragment() {

    private lateinit var expandableMenu1: ScrollView
    private lateinit var binding: FragmentPropertiesBinding

    private lateinit var ivExplore: ImageView
    private lateinit var ivExplore1: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPropertiesBinding.inflate(inflater, container, false)


        val ivExplore: ImageView = binding.ivExplore
        val ivExplore1: ImageView = binding.ivExplore1

        ivExplore.setOnClickListener {
            val intent = Intent(requireContext(), ResidentialActivity::class.java)
            startActivity(intent)
        }

        ivExplore1.setOnClickListener {
            val intent = Intent(requireContext(), CommercialActivity::class.java)
            startActivity(intent)
        }

        expandableMenu1 = binding.expandableMenu1

        return binding.root
    }


}