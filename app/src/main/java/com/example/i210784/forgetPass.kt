package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit



class forgetPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass)

        var login=findViewById<TextView>(R.id.login)
        var sendBtn=findViewById<Button>(R.id.send)
        var backArrow=findViewById<ImageView>(R.id.back_arrow)
        var mAuth= FirebaseAuth.getInstance();
        val num="+923430473955"
        
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


        var callbacks=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                startActivity(Intent(this@forgetPass,homePage::class.java))
            }

            override fun onVerificationFailed(p0: com.google.firebase.FirebaseException) {
                Toast.makeText(this@forgetPass, "Failed to verify", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                var i=Intent(this@forgetPass,verify::class.java)
                i.putExtra("token",p0)
                startActivity(i)
            }

        }
        if (mAuth.currentUser!=null){
            startActivity(Intent(this,verify::class.java))
            finish()
        }

        sendBtn.setOnClickListener {
            var options= PhoneAuthOptions.newBuilder()
                .setPhoneNumber(num)  //here need to change number
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }
}