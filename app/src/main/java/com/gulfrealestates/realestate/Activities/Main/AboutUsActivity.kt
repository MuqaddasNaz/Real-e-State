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
import com.gulfrealestates.realestate.databinding.ActivityAboutUsBinding
import com.gulfrealestates.realestate.databinding.ActivityBookFlatBinding

class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding // Declare binding object


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater) // Initialise binding object
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


        val textView16 = findViewById<TextView>(R.id.textView16)
        val fullText = "Real Estate buyer and investors can explore a variety of UAE & Pakistan properties on GRE."
        val wordsToBold = listOf("UAE", "GRE")

        val spannable = SpannableStringBuilder(fullText)

        for (word in wordsToBold) {
            val startIndex = fullText.indexOf(word)
            if (startIndex != -1) {
                spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, startIndex + word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        textView16.text = spannable

        val textView15 = findViewById<TextView>(R.id.textView15)
        val fullText1 = "GRE (Gulf Real Estate) is one of the leading real estate platforms in UAE and Pakistan. The aim of this project is to provide a user-friendly platform to connect buyers, investors, landlords, sellers, tenants and brokers to expedite different real estate requirements in a convenient manner."
        val wordsToBold1 = listOf("UAE", "GRE (Gulf Real Estate)")

        val spannable1 = SpannableStringBuilder(fullText1)

        for (word in wordsToBold1) {
            val startIndex1 = fullText1.indexOf(word)
            if (startIndex1 != -1) {
                spannable1.setSpan(StyleSpan(Typeface.BOLD), startIndex1, startIndex1 + word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        textView15.text = spannable1

        val textView17 = findViewById<TextView>(R.id.textView17)
        val fullText2 = "Globally trusted real estate agencies are working as the backbone of GRE which provide lots of profitable property opportunities in UAE & Pakistan. For the investment we provide opportunities in Apartments, Townhouses, Villas, Penthouses, Villa Compounds and Residential & Commercial buildings."
        val wordsToBold2 = listOf("UAE", "GRE")

        val spannable2 = SpannableStringBuilder(fullText2)

        for (word in wordsToBold2) {
            val startIndex = fullText2.indexOf(word)
            if (startIndex != -1) {
                spannable2.setSpan(StyleSpan(Typeface.BOLD), startIndex, startIndex + word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        textView17.text = spannable2



    }
}