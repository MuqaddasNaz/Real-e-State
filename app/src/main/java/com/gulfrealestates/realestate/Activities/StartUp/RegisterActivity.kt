package com.gulfrealestates.realestate.Activities.StartUp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.gulfrealestates.realestate.Activities.Main.AddPropertyActivity
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var etAgencyName: EditText
    private lateinit var etUserName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etUserEmail: EditText
    private lateinit var etUserPhoneNumber: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var spPhase: Spinner

    private  var userName = ""
    private  var userEmail = ""
    private  var phoneNumber = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        Functions.setTransparentStatusBar(this)

        etAgencyName = findViewById(R.id.editTextAgencyName)
        etUserName = findViewById(R.id.et_userName)
        etLastName = findViewById(R.id.editTextLastName)
        etUserEmail = findViewById(R.id.et_userEmail)
        etUserPhoneNumber = findViewById(R.id.et_userPhoneNumber)
        etAddress = findViewById(R.id.et_address)
        etPassword = findViewById(R.id.editTextPassword)
        etConfirmPassword = findViewById(R.id.editTextconfirmPwd)
        btnRegister = findViewById(R.id.btn_register)
        tvLogin = findViewById(R.id.textViewLogin)
        checkBox = findViewById(R.id.checkBox)
        spPhase = findViewById(R.id.sp_phase)

        btnRegister.setOnClickListener {
            val agencyName = etAgencyName.text.toString().trim()
            val userName = etUserName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val userEmail = etUserEmail.text.toString().trim()
            val phoneNumber = etUserPhoneNumber.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (userName.isEmpty() || lastName.isEmpty() || agencyName.isEmpty() || userEmail.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val ref = database.getReference("Register")
                val userEmail = ref.push().key
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!checkBox.isChecked) {
                Toast.makeText(this, "Please agree to the terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(userName, lastName, userEmail, phoneNumber, password, agencyName, address)
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser(userName: String, lastName: String, userEmail: String, phoneNumber: String, password: String, agencyName: String, address: String) {
        auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid

                    val ref = FirebaseDatabase.getInstance().getReference("users")
                    val userInfo = HashMap<String, Any>()
                    userInfo["agencyName"] = agencyName
                    userInfo["firstName"] = userName
                    userInfo["lastName"] = lastName
                    userInfo["email"] = userEmail
                    userInfo["phone"] = phoneNumber
                    userInfo["address"] = address
                    userInfo["uid"] = uid ?: ""

                    ref.child(uid ?: "").setValue(userInfo)
                        .addOnCompleteListener { registerTask ->
                            if (registerTask.isSuccessful) {

                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                navigateToAgenciesActivity(userName, userEmail, phoneNumber, agencyName, address)
                                finish()

                            } else {
                                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                } else {

                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToAgenciesActivity(userName: String, userEmail: String, phoneNumber: String, agencyName: String, address: String) {
        val intent = Intent(this, AddPropertyActivity::class.java).apply {
            putExtra("agencyName", agencyName)
            putExtra("firstName", userName)
            putExtra("email", userEmail)
            putExtra("phone", phoneNumber)
            putExtra("address", address)
        }
        startActivity(intent)
    }
}