
package com.example.i210784


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<TextView>(R.id.connect)

        btn.setOnClickListener {
            startActivity(
                Intent(this,
                    login::class.java)
            );
        }

    }
}