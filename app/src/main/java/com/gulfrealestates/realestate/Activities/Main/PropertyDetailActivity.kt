package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.realestates.Utills.Constants
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.databinding.ActivityPropertyDetailBinding

@SuppressWarnings("deprecation")
class PropertyDetailActivity : AppCompatActivity() {

    private var context: Context = this@PropertyDetailActivity

    private lateinit var binding: ActivityPropertyDetailBinding
    private lateinit var property: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Functions.setTransparentStatusBar(context)

        initUI()
        clickListeners()



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


    }


    private fun initUI() {

        property = intent.getSerializableExtra(Constants.propertDetail) as Property
        context = this@PropertyDetailActivity
        setUI()

    }

    private fun clickListeners(){

        binding.toolbar.setNavigationOnClickListener {

            onBackPressed()

        }

    }

    private fun setUI() {

        Glide.with(context).load(property.imgUrl).into(binding.ivProperty)

        binding.tvTitle.text = property.title
        binding.tvBedCount.text = property.bedrooms
        binding.tvPrice.text = property.totalPrice
        binding.tvRange.text = property.city
        binding.tvSaleType.text = property.propertyType
        binding.tvShowerCount.text = property.bathrooms
        binding.tvContactNumber.text = property.contactNumber + "\n" + property.contactNumber1
        binding.tvEmail.text = property.emailAddress


    }


}