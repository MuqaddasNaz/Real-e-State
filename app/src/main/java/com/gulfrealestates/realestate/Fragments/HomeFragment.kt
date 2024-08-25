package com.gulfrealestates.realestate.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gulfrealestates.realestate.Adapter.HomeAdapter
import com.gulfrealestates.realestate.Adapter.HomeAdapter1
import com.gulfrealestates.realestate.Models.AddProperty
import com.gulfrealestates.realestate.Models.Favorites
import com.gulfrealestates.realestate.Models.HomeItem
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), HomeAdapter.HomeAdapterListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var swHome: SwipeRefreshLayout
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sh: SharedPref
    private lateinit var mContext: Context
    private var itemList = ArrayList<HomeItem>()
    private var propertyList = ArrayList<Property>()
    private lateinit var adapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        hideStatusBar()
        setStatusBarColor(R.color.lightBlue)

        val searchBar = view.findViewById<EditText>(R.id.et_searchBar)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val viewFlipper = view.findViewById<ViewFlipper>(R.id.viewFlipper)

        viewFlipper.setInAnimation(requireContext(), android.R.anim.slide_in_left)
        viewFlipper.setOutAnimation(requireContext(), android.R.anim.slide_out_right)
        viewFlipper.isAutoStart = true
        viewFlipper.flipInterval = 3000
        viewFlipper.setOnTouchListener { _, _ -> true }

        sh = SharedPref(mContext)
        recyclerView = view.findViewById(R.id.rv_home)
        swHome = view.findViewById(R.id.sw_home)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

//        itemList = generateHomeItemList()

        adapter = HomeAdapter(mContext, propertyList, this)

        swHome.setOnRefreshListener(this::getData)

        getData()

//        val adapter = HomeAdapter(itemList, object : HomeAdapter.HomeAdapterListener {
//            override fun onLikeButtonClick(view: View, position: Int) {
//                val imageButton = view.findViewById<View>(R.id.btn_like) as ImageButton
//                val btnLike = view.findViewById<ImageButton>(R.id.btn_like)
//
//                val item = itemList[position]
//
//                btnLike.setOnClickListener {
//
//                    item.isLiked = !item.?
//
//                    if (item.isLiked) {
//                        saveToFavorites(item)
//                        imageButton.setImageResource(R.drawable.ic_like2)
//
//                    } else {
//                        removeFavorite(item)
//                        imageButton.setImageResource(R.drawable.ic_like1)
//
//                    }
//
//                }
//
//
//            }
//        })



        return view
    }

    private fun getData() {

        swHome.isRefreshing = true

        FirebaseDatabase.getInstance().getReference("properties")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    propertyList.clear()


                    for (snap in snapshot.children) {

                        val property = snap.getValue(Property::class.java)
                        if (property != null) {
                            propertyList.add(property)
                        }
                    }

                    recyclerView.adapter = adapter

                    swHome.isRefreshing = false

                }

                override fun onCancelled(error: DatabaseError) {

                    swHome.isRefreshing = false

                }

            })
    }

//    private fun generateHomeItemList(): ArrayList<HomeItem> {
//        val itemList = mutableListOf<HomeItem>()
//
//        // Hardcoded property data
//        val properties = listOf(
//            com.example.realestate.Models.Property(
//                R.drawable.property_img2,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img3,
//                "$700,000",
//                "FOR RENT",
//                "2",
//                "1",
//                "2100 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img4,
//                "$100,000",
//                "FOR SALE",
//                "1",
//                "3",
//                "1000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img5,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img6,
//                "PKR 300,000",
//                "FOR RENT",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img7,
//                "$400,000",
//                "FOR SALE",
//                "5",
//                "4",
//                "3000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img8,
//                "PKR 300,000",
//                "FOR RENT",
//                "1",
//                "1",
//                "1200 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img9,
//                "$1300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2200 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img2,
//                "$1400,000",
//                "FOR SALE",
//                "3",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img3,
//                "PKR 3300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img4,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img5,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img6,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ), com.example.realestate.Models.Property(
//                R.drawable.property_img7,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img8,
//            "PKR 300,000",
//            "FOR SALE",
//            "4",
//            "3",
//            "2000 Sq Ft",
//            false
//            ),
//            com.example.realestate.Models.Property(
//                R.drawable.property_img9,
//                "PKR 300,000",
//                "FOR SALE",
//                "4",
//                "3",
//                "2000 Sq Ft",
//                false
//            )
//
//        )
//
//        for (property in properties) {
//            val homeItem = HomeItem(
//                property.imageResource,
//                property.price,
//                property.saleType,
//                property.bedCount,
//                property.showerCount,
//                property.range,
//                property.isLiked
//            )
//            itemList.add(homeItem)
//        }
//
//        return itemList as ArrayList<HomeItem>
//    }

    private fun ViewModelProvider.get(modelClass: Class<Favorites>): Favorites {
        TODO("Not yet implemented")
    }


//    data class com.example.realestate.Models.Property(
//        val imageResource: Int,
//        val price: String,
//        val saleType: String,
//        val bedCount: String,
//        val showerCount: String,
//        val range: String,
//        val isLiked: Boolean
//    )

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
    private fun performSearch(query: String) {


        val filterList = mutableListOf<Property>()
        for (property in propertyList) {

            var name = property.title.lowercase()

            if (name.contains(query.lowercase(), ignoreCase = true)) {
                filterList.add(property)
            }
        }


        adapter = HomeAdapter(mContext, filterList, this)
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()
    }


    private fun hideStatusBar() {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun setStatusBarColor(colorResId: Int) {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), colorResId)
    }
    override fun HomeAdapter1(context: MutableList<AddProperty>): HomeAdapter1 {
        TODO("Not yet implemented")
    }


}

