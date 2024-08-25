package com.gulfrealestates.realestate.Activities.Main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gulfrealestates.realestate.Models.ContactUs
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.LoadingDialog
import com.google.firebase.database.FirebaseDatabase

class ContactUsActivity : AppCompatActivity() {

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var editTextName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        initUI()


        val imageViewBack = findViewById<ImageView>(R.id.icon_back)
        imageViewBack.setOnClickListener {
            onBackPressed()
        }

        val phoneNumberTextView = findViewById<TextView>(R.id.tv_call_number)
        phoneNumberTextView.setOnClickListener {
            val phoneNumber = phoneNumberTextView.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+971507961525")
            startActivity(intent)
        }

        val phoneNumber1TextView = findViewById<TextView>(R.id.tv_call_number1)
        phoneNumber1TextView.setOnClickListener {
            val phoneNumber = phoneNumber1TextView.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+923237878048")
            startActivity(intent)
        }


        editTextName = findViewById(R.id.etName)
        editTextLastName = findViewById(R.id.etLastName)
        editTextEmail = findViewById(R.id.etEmail)
        editTextMessage = findViewById(R.id.etMessage)
        buttonSubmit = findViewById(R.id.btn_submit)

        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString()
            val contactNumber = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val feedback = editTextMessage.text.toString()

            loadingDialog.show()

            if (name.isNotEmpty() && contactNumber.isNotEmpty() && email.isNotEmpty() && feedback.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Feedback")
                val feedbackId = ref.push().key


                if (feedbackId != null) {
                    val contactUs = ContactUs(feedbackId, name, contactNumber, email, feedback)

                    loadingDialog.dismiss()
                    ref.child(feedbackId).setValue(contactUs)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show()
                            editTextName.setText("")
                            editTextLastName.setText("")
                            editTextEmail.setText("")
                            editTextMessage.setText("")
                        }

                        .addOnFailureListener {
                            loadingDialog.dismiss()
                            Toast.makeText(this, "Failed to submit feedback", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                loadingDialog.dismiss()
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initUI() {

        loadingDialog = LoadingDialog(this)

    }
}