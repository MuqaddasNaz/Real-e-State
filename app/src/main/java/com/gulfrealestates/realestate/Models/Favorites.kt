package com.gulfrealestates.realestate.Models

data class Favorites(

    val imageResource: String,
    val id: String = "",
    val title: String = "",
    val price: String = "",
    val saleType: String = "",
    val bedCount: String = "",
    val showerCount: String = "",
    val range: String = "",
    var isLiked: Boolean
)
