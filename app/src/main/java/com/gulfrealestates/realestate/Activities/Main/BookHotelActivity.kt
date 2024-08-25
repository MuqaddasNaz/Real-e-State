package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.databinding.ActivityBookHostelBinding
import com.gulfrealestates.realestate.databinding.ActivityBookHotelBinding

class BookHotelActivity : AppCompatActivity() {


    private lateinit var binding: ActivityBookHotelBinding // Declare binding object


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookHotelBinding.inflate(layoutInflater) // Initialise binding object
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

        val textView14 = findViewById<TextView>(R.id.textView14)
        val fullText1 = "GRE specializes in trip planning in UAE & Pakistan. Starting from the tickets we provide services till the local tourists in UAE & Pakistan. Further, booking a hotel to stay is one of the most important decisions while planning a trip. We can help you in this decision while planning a trip to UAE or Pakistan."
        val wordsToBold1 = listOf("UAE", "GRE")

        val spannable1 = SpannableStringBuilder(fullText1)

        for (word in wordsToBold1) {
            val startIndex1 = fullText1.indexOf(word)
            if (startIndex1 != -1) {
                spannable1.setSpan(StyleSpan(Typeface.BOLD), startIndex1, startIndex1 + word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        textView14.text = spannable1


    }
}