package com.gulfrealestates.realestate.Fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gulfrealestates.realestate.Activities.Main.ServicesActivity
import com.gulfrealestates.realestate.Activities.StartUp.LoginActivity
import com.gulfrealestates.realestate.Models.Users
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.SharedPref
import com.gulfrealestates.realestate.databinding.FragmentProfileBinding
import com.example.realestates.Utills.Constants
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gulfrealestates.realestate.Activities.Main.AboutUsActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mContext: Context
    private lateinit var tvAboutUs: TextView
    private lateinit var tvServices: TextView
    private lateinit var tvLogout: TextView
    private  var tvUserName = ""
    private  var tvEmail = ""


    private lateinit var databaseReference: DatabaseReference
    private  var user = Users()
    private  lateinit var sp:SharedPref
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val context = requireContext()
        sp = SharedPref(context)
        user = sp.getUsers() ?: Users()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
        mContext = requireContext()
        sp = SharedPref(mContext)
        user = sp.getUsers() ?: Users()



        databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(user.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                        val user = snapshot.getValue(Users::class.java)

                        if (user != null) {

                            val name = user.firstName + " " + user.email

                            val userData = Users(user.uid, user.firstName, user.lastName,user.phone,user.email,user.dpUrl)

                            sp.saveUsers(userData)

                            binding.tvUserName.text = user.firstName
                            binding.tvEmail.text = user.email

                //            Toast.makeText(mContext, "Login Successfully"+user.dpUrl, Toast.LENGTH_SHORT).show()
                            Glide.with(mContext).load(user.dpUrl).placeholder(R.drawable.profile).into(binding.ivUserImg)

                        }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun setupClickListeners() {
        binding.tvLogout.setOnClickListener {
            openLogoutDialog()
        }

        binding.tvAboutUs.setOnClickListener {
            val intent = Intent(requireContext(), AboutUsActivity::class.java)
            startActivity(intent)
        }

        binding.tvServices.setOnClickListener {
            val intent = Intent(requireContext(), ServicesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout?")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                signOut()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            .show()
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()

        sp.saveUsers(Users())

        sp.putBoolean(Constants.isLoggedIn,false)

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}
