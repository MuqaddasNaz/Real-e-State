package com.gulfrealestates.realestate.Models

import java.io.Serializable

data class Property(
    var id:String = "",
    var propertyPurpose:String = "",
    var propertyType:String = "",
    var city:String = "",
    var areaSize:String = "",
    var bedrooms:String = "",
    var bathrooms:String = "",
    var title:String = "",
    var totalPrice:String = "",
    var propertyDescription:String = "",
    var emailAddress:String = "",
    var contactNumber:String = "",
    var contactNumber1:String = "",
    var imgUrl:String = "",
    val name: String = ""
): Serializable {

}
