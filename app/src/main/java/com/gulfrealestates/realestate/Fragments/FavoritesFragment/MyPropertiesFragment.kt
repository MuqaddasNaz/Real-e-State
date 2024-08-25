package com.example.Fragments.Favorites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.gulfrealestates.realestate.Adapter.MyPropertyAdapter
import com.gulfrealestates.realestate.Models.PropertiesItem
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyPropertiesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var textViewNoFavorites: TextView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sh: SharedPref
    private lateinit var swTeam: SwipeRefreshLayout
    private lateinit var mContext: Context
    private lateinit var textViewInstructions: TextView
    private var propertiesList = ArrayList<PropertiesItem>()
    private lateinit var ltFavorites: LinearLayout
    private lateinit var adapter: MyPropertyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_properites, container, false)

        sh = SharedPref(mContext)
        recyclerView = view.findViewById(R.id.rv_my_property) // Initialize recyclerView here
        swTeam = view.findViewById(R.id.sw_team)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val propertiesList = generatePropertiesList()

        adapter = MyPropertyAdapter(propertiesList, this)

        swTeam.setOnRefreshListener(this::getData)

        getData()

        recyclerView.adapter = adapter

        // Initialize views
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        textViewNoFavorites = view.findViewById(R.id.textView20)
        textViewInstructions = view.findViewById(R.id.textView21)
        ltFavorites = view.findViewById(R.id.lt_favorites)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (propertiesList.isEmpty()) {
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

        return view
    }


    private fun getData() {

        swTeam.isRefreshing = true

        FirebaseDatabase.getInstance().getReference("My properties")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    propertiesList.clear()


                    for (snap in snapshot.children) {

                        val propertiesItem = snap.getValue(PropertiesItem::class.java)
                        if (propertiesItem != null) {
                            propertiesList.add(propertiesItem)
                        }
                    }

                    recyclerView.adapter = adapter

                    swTeam.isRefreshing = false

                }

                override fun onCancelled(error: DatabaseError) {

                    swTeam.isRefreshing = false

                }

            })
    }
    private fun generatePropertiesList(): List<PropertiesItem> {
        val propertiesItem = mutableListOf<PropertiesItem>()
        return propertiesItem
    }


}
