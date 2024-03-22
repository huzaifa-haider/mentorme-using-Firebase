package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var edit_dp_cover=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_dp_cover)
        var edit_profile=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_profile)
        var dp=findViewById<CircleImageView>(R.id.profile_image)



        val database = Firebase.database
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid
        val myRef = database.getReference("userInfo")
        // Assuming you have an ImageView with the id "profileImageView" in your layout
        currentUserId?.let { userId ->
            val userQuery = myRef.orderByChild("userID").equalTo(userId)
            userQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (snapshot in dataSnapshot.children) {
                        val userInfo = snapshot.getValue(Model::class.java)
                        if (userInfo != null) {

                            val imageUrl = userInfo.dp // Assuming userInfo.dp contains the URL of the image

                            Picasso.get().load(imageUrl).into(dp)

                        } else {
                            // Handle case when user info is null
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Toast.makeText(this@profile, "Failed to read value", Toast.LENGTH_LONG).show()
                }
            })
        }





        edit_profile.setOnClickListener{
            startActivity(
                Intent(this, com.example.i210784.edit_profile::class.java)
            );
        }





    }
}