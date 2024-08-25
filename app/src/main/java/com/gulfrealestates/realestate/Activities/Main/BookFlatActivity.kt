package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.databinding.ActivityAgenciesBinding
import com.gulfrealestates.realestate.databinding.ActivityBookFlatBinding

class BookFlatActivity : AppCompatActivity() {


    private lateinit var binding: ActivityBookFlatBinding // Declare binding object


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookFlatBinding.inflate(layoutInflater) // Initialise binding object
        setContentView(binding.root)


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
    }
}