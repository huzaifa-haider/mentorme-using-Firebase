package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
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

class johnCooper : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_john_cooper)
        var name=findViewById<TextView>(R.id.name_tv)
        var job=findViewById<TextView>(R.id.job_tv)
        var company=findViewById<TextView>(R.id.company_tv)
        var description=findViewById<TextView>(R.id.description)
        var dp=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)
        var drop_review=findViewById<RelativeLayout>(R.id.review_layout)
        var join_community=findViewById<RelativeLayout>(R.id.join_layout)
        var rating=findViewById<TextView>(R.id.rating)
        var book_a_session=findViewById<TextView>(R.id.book_session)


        var userId=intent.getStringExtra("userID")

        val database = Firebase.database
        val auth = FirebaseAuth.getInstance()
        val myRef = database.getReference("userInfo")

            val userQuery = myRef.orderByChild("userID").equalTo(userId)
            userQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (snapshot in dataSnapshot.children) {
                        val userInfo = snapshot.getValue(Model::class.java)
                        if (userInfo != null) {
                            // Update UI with user info
                            name.setText(userInfo.name)
                            job.setText(userInfo.job + " at ")
                            company.setText(userInfo.company)
                            description.setText(userInfo.description)
                            rating.setText(userInfo.review.toString())


                            val imageUrl = userInfo.dp.toString() // Assuming userInfo.dp contains the URL of the image
                            if(imageUrl!=""){
                                Picasso.get().load(imageUrl).into(dp)
                            }


                        } else {
                            Toast.makeText(this@johnCooper, "User not found", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Toast.makeText(this@johnCooper, "Failed to read value", Toast.LENGTH_LONG).show()
                }
            })


        drop_review.setOnClickListener{
            startActivity(
                Intent(this, dropReview::class.java)
                    .putExtra("userID",userId)
            )
        }

        book_a_session.setOnClickListener {
            startActivity(
                Intent(this,book_session::class.java)
                    .putExtra("userID",userId)
            )
        }
        join_community.setOnClickListener {
            startActivity(
                Intent(this,john_community::class.java)
                    .putExtra("userID",userId)
            )
        }






    }
}