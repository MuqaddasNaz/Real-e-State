package com.gulfrealestates.realestate.Models

class Register() {

    var agencyName: String = ""
    var userName: String = ""
    var userEmail: String = ""
    var phoneNumber: String = ""
    var address: String = ""


    constructor(userName: String, userEmail: String, phoneNumber: String, agencyName: String, address: String) : this() {


        this.agencyName = agencyName
        this.userName = userName
        this.userEmail = userEmail
        this.phoneNumber = phoneNumber
        this.address = address

    }
}


