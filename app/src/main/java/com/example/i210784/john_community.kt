package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso

class john_community : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_john_community)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var name=findViewById<TextView>(R.id.name)
        var userId=intent.getStringExtra("userID")
        var dp=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)


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


                            val imageUrl = userInfo.dp.toString() // Assuming userInfo.dp contains the URL of the image
                            if(imageUrl!=""){
                                Picasso.get().load(imageUrl).into(dp)
                            }
                    } else {
                        Toast.makeText(this@john_community, "User not found", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@john_community, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })


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