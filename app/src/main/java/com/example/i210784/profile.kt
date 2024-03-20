package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var edit_dp=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_dp)
        var edit_profile=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_profile)

        edit_profile.setOnClickListener{
            startActivity(
                Intent(this, com.example.i210784.edit_profile::class.java)
            );
        }
    }
}