package com.gulfrealestates.realestate.Activities.StartUp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gulfrealestates.realestate.Activities.Main.MainActivity
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.Functions
import com.gulfrealestates.realestate.Utills.SharedPref

class SplashActivity : AppCompatActivity() {

    private var context: Context = this@SplashActivity
    private lateinit var sp: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initUI()



        Handler(Looper.getMainLooper()).postDelayed({

            finish()

//            if(sp.getBoolean(Constants.isLoggedIn)) {

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("refreshHomeFragment", true)
                setResult(Activity.RESULT_OK, intent)
                startActivity(intent)

//            }else{
//
//                startActivity(Intent(this, LoginActivity::class.java))
//
//            }


        }, 2000)
    }

    private fun initUI() {

        sp = SharedPref(context)

    }
}