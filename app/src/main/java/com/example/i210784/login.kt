package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var signBtn=findViewById<TextView>(R.id.signUp)
        var login=findViewById<Button>(R.id.loginBtn)
        var forget_pass=findViewById<TextView>(R.id.forget_pass)


        forget_pass.setOnClickListener{
            startActivity(
                Intent(this,forgetPass::class.java)

            );
        }
        signBtn.setOnClickListener{
            startActivity(
                Intent(this,register::class.java)

            );
        }

        login.setOnClickListener{
            startActivity(
                Intent(this,homePage::class.java)

            );
        }


    }
}