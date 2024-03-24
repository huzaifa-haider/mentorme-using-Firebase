package com.example.i210784

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class audio_call : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_call)
        var dp=findViewById<CircleImageView>(R.id.profile_image)
        var userId=intent.getStringExtra("userID")

        val database = Firebase.database
        val myRef = database.getReference("userInfo")
        val userQuery = myRef.orderByChild("userID").equalTo(userId)

        userQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val userInfo = snapshot.getValue(Model::class.java)
                    if (userInfo != null) {
                        // Update UI with user info
                        val imageUrl = userInfo.dp.toString() // Assuming userInfo.dp contains the URL of the image
                        if(imageUrl!=""){
                            Picasso.get().load(imageUrl).into(dp)
                        }


                    } else {
                        Toast.makeText(this@audio_call, "User not found", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@audio_call, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })


    }
}