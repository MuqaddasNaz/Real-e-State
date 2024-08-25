package com.example.Fragments.Favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.gulfrealestates.realestate.Adapter.FavoritesAdapter
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.R

class MyFavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: FavoritesAdapter? = null // Initialize adapter as null
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var textViewNoFavorites: TextView
    private lateinit var textViewInstructions: TextView
    private lateinit var ltFavorites: LinearLayout

    private lateinit var mContext: Context
    private lateinit var sh: SharedPref

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_favorite, container, false)

        sh = SharedPref(requireContext())
        recyclerView = view.findViewById(R.id.rv_favorites)
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        textViewNoFavorites = view.findViewById(R.id.textView20)
        textViewInstructions = view.findViewById(R.id.textView21)
        ltFavorites = view.findViewById(R.id.lt_favorites)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val favoriteList = sh.getFavoritesList()

        adapter = favoriteList?.let { FavoritesAdapter(requireContext(), it) }

        recyclerView.adapter = adapter

        if (favoriteList != null) {
            if (favoriteList.isEmpty()) {
                ltFavorites.visibility = View.VISIBLE
                lottieAnimationView.visibility = View.VISIBLE
                textViewNoFavorites.visibility = View.VISIBLE
                textViewInstructions.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                ltFavorites.visibility = View.GONE
                lottieAnimationView.visibility = View.GONE
                textViewNoFavorites.visibility = View.GONE
                textViewInstructions.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        return view
    }
}
