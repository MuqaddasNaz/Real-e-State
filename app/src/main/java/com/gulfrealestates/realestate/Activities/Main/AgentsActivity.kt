package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.databinding.ActivityAgenciesBinding
import com.gulfrealestates.realestate.databinding.ActivityAgentsBinding

class AgentsActivity : AppCompatActivity() {

    private var context: Context = this@AgentsActivity

    private lateinit var binding: ActivityAgentsBinding // Declare binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgentsBinding.inflate(layoutInflater) // Initialise binding object
        setContentView(binding.root)

        Functions.setTransparentStatusBar(context)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val btnSearchAgents: AppCompatButton = findViewById(R.id.btn_searchAgents)

        btnSearchAgents.setOnClickListener {
            // Add your code here to display a toast message when the button is clicked
            Toast.makeText(this, "Search Agents not Founded!", Toast.LENGTH_SHORT).show()
        }
        val tvViewListing1: TextView = findViewById(R.id.tv_viewListing1)

        tvViewListing1.setOnClickListener {
            val intent = Intent(this, ViewListing1Activity::class.java)
            startActivity(intent)
        }


    }
}