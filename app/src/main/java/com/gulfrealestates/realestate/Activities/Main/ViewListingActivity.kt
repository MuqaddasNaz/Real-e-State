package com.gulfrealestates.realestate.Activities.Main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.gulfrealestates.realestate.Models.ContactUs
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.LoadingDialog
import com.gulfrealestates.realestate.databinding.ActivityViewListingBinding
import com.google.firebase.database.FirebaseDatabase

class ViewListingActivity : AppCompatActivity() {

    private var context: Context = this@ViewListingActivity

    private lateinit var layoutReview: LinearLayout

    private lateinit var loadingDialog: LoadingDialog


    private lateinit var editTextYourEmail: EditText
    private lateinit var editTextTitle: EditText
    private lateinit var spRating: Spinner
    private lateinit var editTextReviews: EditText
    private lateinit var buttonSubmitReview: Button

    private lateinit var binding: ActivityViewListingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

        Functions.setTransparentStatusBar(context)

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

        binding.btnSubmitReview.setOnClickListener {
            val email = binding.etYourEmail.text.toString()
            val title = binding.etTitle.text.toString()
            val rating = binding.spRating.selectedItem.toString()
            val review = binding.etReviews.text.toString()

            if (email.isNotEmpty() && title.isNotEmpty() && review.isNotEmpty()) {
                loadingDialog.show() // Show loading indicator when review submission starts

                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Reviews")
                val feedbackId = ref.push().key

                if (feedbackId != null) {
                    val contactUs = ContactUs(feedbackId, email, title, rating, review)

                    ref.child(feedbackId).setValue(contactUs)
                        .addOnSuccessListener {
                            loadingDialog.dismiss() // Dismiss loading indicator on successful review submission
                            Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show()
                            binding.etYourEmail.setText("")
                            binding.etTitle.setText("")
                            binding.spRating.setSelection(0) // Set spinner selection to default
                            binding.etReviews.setText("")
                        }
                        .addOnFailureListener {
                            loadingDialog.dismiss() // Dismiss loading indicator on review submission failure
                            Toast.makeText(this, "Failed to submit reviews", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.layoutReview.visibility = View.GONE

        binding.tvReviews.setOnClickListener {
            if (binding.layoutReview.visibility == View.VISIBLE) {
                binding.layoutReview.visibility = View.GONE
            } else {
                binding.layoutReview.visibility = View.VISIBLE
            }
        }

        binding.textView38.setOnClickListener {
            binding.textView38.setBackgroundResource(R.drawable.bg_edittext_password_yellow)
            binding.tvReviews.setBackgroundResource(R.drawable.bg_edittext_password)
        }

        binding.tvReviews.setOnClickListener {
            binding.textView38.setBackgroundResource(R.drawable.bg_edittext_password)
            binding.tvReviews.setBackgroundResource(R.drawable.bg_edittext_password_yellow)
            binding.layoutReview.visibility = View.VISIBLE
        }



    }

    private fun initUI() {



        loadingDialog = LoadingDialog(this)
    }


}
