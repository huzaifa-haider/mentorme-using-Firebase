package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class resetPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)
        var loginTextBtn=findViewById<TextView>(R.id.login)
        var resetBtn=findViewById<Button>(R.id.resetBtn)
        var backArrow=findViewById<ImageView>(R.id.back_arrow)


        backArrow.setOnClickListener {
            startActivity(
                Intent(this,forgetPass::class.java)
            );
        }
        resetBtn.setOnClickListener {
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