package com.example.i210784

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent

class notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        var backArrow=findViewById<ImageView>(R.id.back_arrow)

        backArrow.setOnClickListener {
            startActivity(
                Intent(this,homePage::class.java)
            );
        }
    }
}