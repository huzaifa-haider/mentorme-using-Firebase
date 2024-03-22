package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout

class chats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var johnCooper_chat=findViewById<LinearLayout>(R.id.john_cooper_chat)

        johnCooper_chat.setOnClickListener {
            startActivity(
                Intent(this,john_cooper_chat::class.java)
            );
        }



        chat.setOnClickListener {
            startActivity(
                Intent(this,chats::class.java)
            );
        }
        home.setOnClickListener {
            startActivity(
                Intent(this,homePage::class.java)
            );
        }
        add.setOnClickListener {
            startActivity(
                Intent(this,add_mentor::class.java)
            );
        }

        search.setOnClickListener {
            startActivity(
                Intent(this,search::class.java)
            );
        }

    }
}