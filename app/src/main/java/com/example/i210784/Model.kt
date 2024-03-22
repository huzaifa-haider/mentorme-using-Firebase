package com.example.i210784

data class Model(val userID:String,val name:String, val email:String, val contact:String,val country:String,val city:String, val dp:String) {
    constructor():this("","","","","","","")
}