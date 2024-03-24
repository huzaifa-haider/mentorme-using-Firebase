package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso

class book_session : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_session)
        var userId=intent.getStringExtra("userID")

        var name=findViewById<TextView>(R.id.name)
        var rating=findViewById<TextView>(R.id.rating)
        var price=findViewById<TextView>(R.id.price)
        var time1=findViewById<TextView>(R.id.time1)
        var time2=findViewById<TextView>(R.id.time2)
        var time3=findViewById<TextView>(R.id.time3)
        var book_appointment=findViewById<TextView>(R.id.book_appointment)
        var dp=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)
        var chat=findViewById<ImageView>(R.id.chat)
        var voice_call=findViewById<ImageView>(R.id.voice_call)
        var video_call=findViewById<ImageView>(R.id.video_call)

        val database = Firebase.database
        val myRef = database.getReference("userInfo")
        val userQuery = myRef.orderByChild("userID").equalTo(userId)

        userQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val userInfo = snapshot.getValue(Model::class.java)
                    if (userInfo != null) {
                        // Update UI with user info
                        name.setText(userInfo.name.toString())
                        rating.setText(userInfo.review.toString())
                        price.setText("$"+userInfo.price+"/session")


                            val imageUrl = userInfo.dp // Assuming userInfo.dp contains the URL of the image
                            if(imageUrl!=""){
                                Picasso.get().load(imageUrl).into(dp)
                            }


                    } else {
                        Toast.makeText(this@book_session, "User not found", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@book_session, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })

        chat.setOnClickListener{
            startActivity(
                Intent(this, john_cooper_chat::class.java)
                    .putExtra("userID",userId)
            )
        }
        voice_call.setOnClickListener{
            startActivity(
                Intent(this, audio_call::class.java)
                    .putExtra("userID",userId)
            )
        }
        video_call.setOnClickListener{
            startActivity(
                Intent(this, video_call::class.java)
                    .putExtra("userID",userId)
            )
        }





    }
}