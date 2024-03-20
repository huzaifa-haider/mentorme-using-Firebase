package com.example.i210784


import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth



class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var signBtn=findViewById<TextView>(R.id.signUp)
        var login=findViewById<Button>(R.id.loginBtn)
        var forget_pass=findViewById<TextView>(R.id.forget_pass)
        var email=findViewById<EditText>(R.id.emailEdit)
        var pass=findViewById<EditText>(R.id.passEdit)
        var mAuth=FirebaseAuth.getInstance();
        var rand=findViewById<Button>(R.id.randBtn)


        rand.setOnClickListener{
            startActivity(
                Intent(this,forgetPass::class.java)
            );
        }



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
            mAuth.signInWithEmailAndPassword(email.text.toString(),pass.text.toString())
                .addOnSuccessListener { authResult ->
                    startActivity(Intent(this,homePage::class.java))
                    finish();
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to SignIn", Toast.LENGTH_SHORT).show()
                }

        }
        if(mAuth.currentUser!=null){
            startActivity(Intent(this,homePage::class.java))
            finish()
        }




    }

}