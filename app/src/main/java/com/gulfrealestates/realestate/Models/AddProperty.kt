package com.gulfrealestates.realestate.Models

data class AddProperty(

    val imageResource: Int,
    val price: String,
    val saleType: String,
    val bedCount: String,
    val showerCount: String,
    val range: String,
    val contactNumber: String,
    var isLiked: Boolean
)
