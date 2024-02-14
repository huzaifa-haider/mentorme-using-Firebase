package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var loginTextBtn=findViewById<TextView>(R.id.loginClick)
        var signUp=findViewById<Button>(R.id.signUpBtn)


        signUp.setOnClickListener {
            startActivity(
                Intent(this,login::class.java)
            );
        }
        loginTextBtn.setOnClickListener {
            startActivity(
                Intent(this,login::class.java)
            );
        }

    }
}