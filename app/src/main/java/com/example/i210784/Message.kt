package com.example.i210784
data class Message(val senderId: String = "",val receiverId:String, val text: String = "", val timestamp: String = "") {
    constructor():this("","","","")
}
