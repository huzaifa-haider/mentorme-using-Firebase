package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class forgetPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass)
        var login=findViewById<TextView>(R.id.login)
        var sendBtn=findViewById<Button>(R.id.send)
        var backArrow=findViewById<ImageView>(R.id.back_arrow)

        backArrow.setOnClickListener {
            startActivity(
                Intent(this, login::class.java)
            );
        }
        login.setOnClickListener {
            startActivity(
                Intent(this, login::class.java)
            );
        }
        sendBtn.setOnClickListener {
            startActivity(
                Intent(this, verify::class.java)
            );
        }
    }
}