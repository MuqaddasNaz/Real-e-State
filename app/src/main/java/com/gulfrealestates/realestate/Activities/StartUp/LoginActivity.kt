package com.gulfrealestates.realestate.Activities.StartUp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gulfrealestates.realestate.Models.Users
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.LoadingDialog
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.Activities.Main.AddPropertyActivity
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.FireRef
import com.gulfrealestates.realestate.databinding.ActivityLoginBinding
import com.example.realestates.Utills.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var context: Context = this@LoginActivity

    private lateinit var auth: FirebaseAuth
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fireRef: FireRef
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: Button
    private var email = ""
    private var password = ""
    private lateinit var tvSignUp: TextView

    private lateinit var sp: SharedPref
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        clickListeners()

        Functions.setTransparentStatusBar(context)

        auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

        if (auth.currentUser != null) {
            navigateToAddPropertyActivity()
            finish()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInClient.signOut()

        val btnGoogle = findViewById<Button>(R.id.btn_google)
        btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun navigateToAddPropertyActivity() {
        val intent = Intent(this, AddPropertyActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "LoginActivity"
    }
    private fun initUI() {

        loadingDialog = LoadingDialog(context)
        sp = SharedPref(context)

        Functions.setTransparentStatusBar(context)

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    getUsersData()
                    //navigateToAddPropertyActivity()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
//        dp = Objects.requireNonNull(user.photoUrl).toString()

    }

    private fun clickListeners() {

        binding.tvSignup.setOnClickListener {

            startActivity(Intent(context, RegisterActivity::class.java))

        }

        binding.btnLogin.setOnClickListener {

            email = binding.etEmail.text.toString().trim()
            password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {

                binding.etEmail.error = "Required"

            }else if (!Functions.isValidEmail(email)) {

                binding.etEmail.error = "Email not valid"

            }else if (password.isEmpty()) {

                binding.etPassword.error = "Required"

            } else if (password.length < 6) {

                Toast.makeText(
                    context, "Password length must be greater than 6", Toast.LENGTH_SHORT
                ).show()

            } else {

                loginUser()

            }

        }

    }

    private fun loginUser() {

        loadingDialog.show()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {

                    getUsersData()


                } else {

                    loadingDialog.dismiss()
                    Toast.makeText(context, "Error: " + it.exception?.message, Toast.LENGTH_SHORT)
                        .show()


                }

            }

    }

    private fun getUsersData() {


        if (FirebaseAuth.getInstance().currentUser?.uid != null) {

            val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

            val ref = FirebaseDatabase.getInstance().getReference(Constants.users)

            ref.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val usersData = snapshot.getValue(Users::class.java)

                    if (usersData != null) {

                        saveToSharedPref(usersData)

                    }else{

                        saveUserData()

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })


        } else {

            loadingDialog.dismiss()
            Toast.makeText(
                context, "Error: An internal error, Please try again!", Toast.LENGTH_SHORT
            ).show()


        }


    }

    private fun saveToSharedPref(usersData: Users) {

        Toast.makeText(this@LoginActivity, "Logged in successfully" + usersData.uid, Toast.LENGTH_SHORT).show()

        sp.saveUsers(usersData)
        loadingDialog.dismiss()

        sp.putBoolean(Constants.isLoggedIn,true)

        val intent = Intent(this, AddPropertyActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)


    }

    private fun LoadingDialog(context: LoginActivity): LoadingDialog {
        val loadingDialog = LoadingDialog(context)
        loadingDialog.show()
        loadingDialog.dismiss()
        return loadingDialog
    }

    fun saveUserData(){

        if (FirebaseAuth.getInstance().currentUser!=null) {

            var user = FirebaseAuth.getInstance().currentUser
            var fullName = user?.displayName.toString()
            var email = user?.email ?: ""
            var uid = user?.uid ?: ""
            var dp = user?.photoUrl.toString() ?: ""

            Toast.makeText(context, "user name: "+fullName, Toast.LENGTH_SHORT).show()
            var firstName = ""
            var lastName = ""

            val parts: List<String> = fullName.split(" ")
            if (parts.size == 2) {

                firstName = parts[0]
                lastName = parts[1]

            } else if (parts.size == 3) {

                firstName = parts[0]
                lastName = parts[1]
                lastName += parts[2]

            }else{

                firstName = fullName

            }

            val ref = FirebaseDatabase.getInstance().getReference("users")
            val userInfo = HashMap<String, Any>()
            userInfo["firstName"] = firstName
            userInfo["lastName"] = lastName
            userInfo["email"] = email
            userInfo["phone"] = ""
            userInfo["uid"] = uid
            userInfo["dpUrl"] = dp

            ref.child(uid).setValue(userInfo)
                .addOnCompleteListener { registerTask ->
                    if (registerTask.isSuccessful) {

                        var user = Users(uid,firstName,lastName,"",email,dp)
                        saveToSharedPref(user)

                   } else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                   }
                }


        }
    }

}
