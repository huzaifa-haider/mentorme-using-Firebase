package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class verify : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        var backArrow=findViewById<ImageView>(R.id.back_arrow)
        var verifyBtn=findViewById<ImageView>(R.id.back_arrow)



        verifyBtn.setOnClickListener {
            startActivity(
                Intent(this,resetPass::class.java)
            );
        }
        backArrow.setOnClickListener {
            startActivity(
                Intent(this,forgetPass::class.java)
            );
        }
    }
}