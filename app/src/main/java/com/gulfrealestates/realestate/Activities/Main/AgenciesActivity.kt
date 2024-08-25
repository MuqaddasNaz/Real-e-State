package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gulfrealestates.realestate.Adapter.AgencyAdapter
import com.gulfrealestates.realestate.Models.Register
import com.gulfrealestates.realestate.Models.Users
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.databinding.ActivityAgenciesBinding
import com.google.firebase.database.*
import com.gulfrealestates.realestate.R

class AgenciesActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnSearchAgency: Button
    private lateinit var tvViewListing: TextView
    private lateinit var adapter: AgencyAdapter
    private lateinit var agencyList: MutableList<Register>
    private lateinit var binding: ActivityAgenciesBinding
    private lateinit var databaseReference: DatabaseReference
    private  var userData = Users()

    private  lateinit var sp:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgenciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this@AgenciesActivity

        sp = SharedPref(context)
        if(sp.getUsers()!=null) {
            userData = sp.getUsers()!!
        }


        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        val tvEmailInfo = findViewById<TextView>(R.id.tv_email_info)
        tvEmailInfo.setOnClickListener {
            val email = tvEmailInfo.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@gulfrealestates.org")
            startActivity(intent)
        }

        val phoneNumberTextView = findViewById<TextView>(R.id.tv_call_number)
        phoneNumberTextView.setOnClickListener {
            val phoneNumber = phoneNumberTextView.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+923237878048")
            startActivity(intent)
        }

        val phoneNumber1TextView = findViewById<TextView>(R.id.tv_call_number1)
        phoneNumber1TextView.setOnClickListener {
            val phoneNumber = phoneNumber1TextView.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+971507961525")
            startActivity(intent)
        }


        tvViewListing = binding.textView0
        btnSearchAgency = binding.btnSearchAgency
        recyclerView = binding.rvAgency
        recyclerView.layoutManager = LinearLayoutManager(this)
        agencyList = mutableListOf()
        adapter = AgencyAdapter(agencyList, this)
        recyclerView.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    agencyList.clear()

                    for(dataSnapshot in snapshot.children) {

                        val user = dataSnapshot.getValue(Users::class.java)

                        if (user != null && user.agencyName.isNotEmpty()) {
                            // Check if the user has not registered (uid is empty)
                            val name = user.firstName + " " + user.lastName
                            val agency = Register(name, user.email, user.phone, user.agencyName, user.address)
                            agencyList.add(agency)
                        }else{

                            (user != null)


                        }

                    }

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AgenciesActivity, "Failed to retrieve agency", Toast.LENGTH_SHORT).show()
            }
        })


        val etSearchAgency = findViewById<EditText>(R.id.etSearchAgency)

        etSearchAgency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

//        btnSearchAgency.setOnClickListener {
//            Toast.makeText(this, "Search Agency not Founded!", Toast.LENGTH_SHORT).show()
//        }

        tvViewListing.setOnClickListener {
            val intent = Intent(this, ViewListingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performSearch(query: String) {
        val filterList = mutableListOf<Register>()
        for (register in agencyList) {
            val name = register.agencyName.lowercase()
            if (name.contains(query.lowercase(), ignoreCase = true)) {
                filterList.add(register)
            }
        }

        adapter = AgencyAdapter(filterList, this)
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()
    }

}
