package com.example.i210784

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class dropReview : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_review)
        var star1=findViewById<ImageView>(R.id.star1)
        var star2=findViewById<ImageView>(R.id.star2)
        var star3=findViewById<ImageView>(R.id.star3)
        var star4=findViewById<ImageView>(R.id.star4)
        var star5=findViewById<ImageView>(R.id.star5)
        var submit=findViewById<TextView>(R.id.submit_feed)
        var dp=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)
        var userId=intent.getStringExtra("userID")
        var rating=0

        star1.setOnClickListener {
            star1.setImageResource(R.drawable.star)
            star1.setColorFilter(resources.getColor(R.color.yellow))
            rating=1
        }
        star2.setOnClickListener {
            star1.setImageResource(R.drawable.star)
            star1.setColorFilter(resources.getColor(R.color.yellow))
            star2.setImageResource(R.drawable.star)
            star2.setColorFilter(resources.getColor(R.color.yellow))
            rating=2
        }
        star3.setOnClickListener {
            star1.setImageResource(R.drawable.star)
            star1.setColorFilter(resources.getColor(R.color.yellow))
            star2.setImageResource(R.drawable.star)
            star2.setColorFilter(resources.getColor(R.color.yellow))
            star3.setImageResource(R.drawable.star)
            star3.setColorFilter(resources.getColor(R.color.yellow))
            rating=3
        }
        star4.setOnClickListener {
            star1.setImageResource(R.drawable.star)
            star1.setColorFilter(resources.getColor(R.color.yellow))
            star2.setImageResource(R.drawable.star)
            star2.setColorFilter(resources.getColor(R.color.yellow))
            star3.setImageResource(R.drawable.star)
            star3.setColorFilter(resources.getColor(R.color.yellow))
            star4.setImageResource(R.drawable.star)
            star4.setColorFilter(resources.getColor(R.color.yellow))
            rating=4

        }
        star5.setOnClickListener {

            star1.setImageResource(R.drawable.star)
            star1.setColorFilter(resources.getColor(R.color.yellow))
            star2.setImageResource(R.drawable.star)
            star2.setColorFilter(resources.getColor(R.color.yellow))
            star3.setImageResource(R.drawable.star)
            star3.setColorFilter(resources.getColor(R.color.yellow))
            star4.setImageResource(R.drawable.star)
            star4.setColorFilter(resources.getColor(R.color.yellow))
            star5.setImageResource(R.drawable.star)
            star5.setColorFilter(resources.getColor(R.color.yellow))
            rating=5

        }

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
                        Toast.makeText(this@dropReview, "User not found", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@dropReview, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })



        submit.setOnClickListener {
                val db = Firebase.database.getReference("userInfo")
                val userQuery = db.orderByChild("userID").equalTo(userId)
                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val userKey = snapshot.key
                            if (userKey != null) {
                                db.child(userKey).child("review").setValue(rating)
                                    .addOnSuccessListener {
                                        Toast.makeText(this@dropReview, "Thanks for your feedback", Toast.LENGTH_LONG).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this@dropReview, "Failed to update", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@dropReview, "Failed to read value", Toast.LENGTH_LONG).show()
                    }
                })

        }




    }
}