package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class add_mentor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mentor)


        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var backArrow=findViewById<ImageView>(R.id.back_arrow)
        var upload_photo=findViewById<LinearLayout>(R.id.upload_photo_layout)
        var upload_video=findViewById<LinearLayout>(R.id.upload_video_layout)
        var uploadBtn=findViewById<TextView>(R.id.upload)


        uploadBtn.setOnClickListener {
            startActivity(
                Intent(this,homePage::class.java)
            );
        }
        backArrow.setOnClickListener {
            startActivity(
                Intent(this,homePage::class.java)
            );
        }

        upload_photo.setOnClickListener {
            startActivity(
                Intent(this,photo::class.java)
            );
        }

        upload_video.setOnClickListener {
            startActivity(
                Intent(this,video::class.java)
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