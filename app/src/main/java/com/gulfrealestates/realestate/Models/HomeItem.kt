package com.gulfrealestates.realestate.Models

data class HomeItem(
    val imageResource: String,
    val price: String,
    val saleType: String,
    val bedCount: String,
    val showerCount: String,
    val range: String,
    var isLiked: Boolean
)