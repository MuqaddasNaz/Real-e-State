package com.gulfrealestates.realestate.Utills
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object FireRef {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val TASKS: CollectionReference = FirebaseFirestore.getInstance().collection("tasks")
}
