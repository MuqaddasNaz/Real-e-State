package com.gulfrealestates.realestate.Activities.Main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gulfrealestates.realestate.Adapter.CommercialAdapter
import com.gulfrealestates.realestate.Adapter.ResidentialAdapter
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.databinding.ActivityAgenciesBinding
import com.gulfrealestates.realestate.databinding.ActivityCommercialBinding
import com.gulfrealestates.realestate.databinding.ActivityResidentialBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ResidentialActivity : AppCompatActivity(), ResidentialAdapter.ResidentialAdapterListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swHome: SwipeRefreshLayout
    private lateinit var sh: SharedPref
    private lateinit var mContext: Context
    private lateinit var adapter: ResidentialAdapter

    private lateinit var binding: ActivityResidentialBinding // Declare binding object

    private var propertyList = ArrayList<Property>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResidentialBinding.inflate(layoutInflater) // Initialise binding object
        setContentView(binding.root)


        val searchBar = findViewById<EditText>(R.id.et_searchBar)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        mContext = this

        recyclerView = findViewById(R.id.rv_residential)
        swHome = findViewById(R.id.sw_home)

        recyclerView.layoutManager = LinearLayoutManager(mContext)

        val adapter = ResidentialAdapter(mContext, propertyList, this)

        recyclerView.adapter = adapter

        swHome.setOnRefreshListener { getData() }

        getData()
    }

    private fun performSearch(query: String) {


        val filterList = mutableListOf<Property>()
        for (property in propertyList) {

            var name = property.title.lowercase()

            if (name.contains(query.lowercase(), ignoreCase = true)) {
                filterList.add(property)
            }
        }


        adapter = ResidentialAdapter(mContext, filterList, this)
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()
    }
    private fun getData() {
        swHome.isRefreshing = true

        FirebaseDatabase.getInstance().getReference("properties")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    propertyList.clear()

                    for (snap in snapshot.children) {
                        val property = snap.getValue(Property::class.java)
                        property?.let {
                            propertyList.add(it)
                        }
                    }

                    recyclerView.adapter?.notifyDataSetChanged()

                    swHome.isRefreshing = false
                }

                override fun onCancelled(error: DatabaseError) {
                    swHome.isRefreshing = false
                    Toast.makeText(mContext, "Failed to fetch data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onLikeButtonClick(view: View, position: Int) {
        val imageButton = view.findViewById<ImageButton>(R.id.btn_like)
        val btnLike = view.findViewById<ImageButton>(R.id.btn_like)

        val item = propertyList[position]

        btnLike.setOnClickListener {

            Toast.makeText(mContext, "Click" , Toast.LENGTH_SHORT).show()

            val favList = sh.getFavoritesList()

            val findItem = favList?.find { it.id == item.id }

            val isLiked: Boolean = findItem != null

            Toast.makeText(mContext, "isLiked" + isLiked, Toast.LENGTH_SHORT).show()

            if (!isLiked) {
                sh.saveToFav(item)
                imageButton.setImageResource(R.drawable.ic_like2)

            } else {
                sh.removeFavorite(item)
                imageButton.setImageResource(R.drawable.ic_like1)

            }

            adapter.notifyDataSetChanged()

        }

    }
}
