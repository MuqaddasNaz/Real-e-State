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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.databinding.ActivityResidentialBinding
import com.gulfrealestates.realestate.databinding.ActivityServicesBinding

class ServicesActivity : AppCompatActivity() {


    private lateinit var binding: ActivityServicesBinding // Declare binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesBinding.inflate(layoutInflater) // Initialise binding object
        setContentView(binding.root)


        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
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



        val tvEmailInfo = findViewById<TextView>(R.id.tv_email_info)
        tvEmailInfo.setOnClickListener {
            val email = tvEmailInfo.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@gulfrealestates.org")
            startActivity(intent)
        }


        val whatsappIcon: ImageView = findViewById(R.id.watsapp_icn)

        whatsappIcon.setOnClickListener {

            val number = "+971507961525" // WhatsApp number yahan daalein
            val url = "https://api.whatsapp.com/send?phone=$number"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "WhatsApp is not installed on your device.", Toast.LENGTH_SHORT).show()
            }
        }


        val whatsappIcon1: ImageView = findViewById(R.id.watsapp_icn1)

        whatsappIcon1.setOnClickListener {

            val number = "+971507961525" // WhatsApp number yahan daalein
            val url = "https://api.whatsapp.com/send?phone=$number"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "WhatsApp is  installed on your device.", Toast.LENGTH_SHORT).show()
            }
        }

        val textView14 = findViewById<TextView>(R.id.textView14)
        val fullText = "With a team of paint experts, GRE offers new and repaints for your valued property. We provide new paint and repaint services for GRE."
        val wordToItalic = "GRE"

        val spannable = SpannableStringBuilder(fullText)
        var startIndex = fullText.indexOf(wordToItalic)

        while (startIndex != -1) {
            spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, startIndex + wordToItalic.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            startIndex = fullText.indexOf(wordToItalic, startIndex + 1)
        }
        textView14.text = spannable

        val textView16 = findViewById<TextView>(R.id.textView16)
        val fullText1 = "Starting from the highly professional construction services, GRE provides experts for complete maintenances of Apartments, Townhouses, Villas, Penthouses, Villa Compounds and Residential & Commercial buildings."
        val wordToItalic1 = "GRE"

        val spannable1 = SpannableStringBuilder(fullText1)
        var startIndex1 = fullText1.indexOf(wordToItalic1)

        while (startIndex1 != -1) {
            spannable1.setSpan(StyleSpan(Typeface.BOLD), startIndex1, startIndex1 + wordToItalic1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            startIndex1 = fullText1.indexOf(wordToItalic1, startIndex1 + 1)
        }

        textView16.text = spannable1


    }

}