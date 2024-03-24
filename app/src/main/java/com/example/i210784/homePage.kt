package com.example.i210784

import com.example.i210784.Model
import com.example.i210784.mentorAdapter
import com.example.i210784.profile
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.i210784.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class homePage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var name=findViewById<TextView>(R.id.name_tv)
        var myProfile=findViewById<ImageView>(R.id.profile_icon)
        var logout=findViewById<Button>(R.id.logout)
        var top_mentor_rv=findViewById<RecyclerView>(R.id.top_mentor_rv)



        val database = com.google.firebase.Firebase.database
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid
        val myRef = database.getReference("userInfo")

        currentUserId?.let { userId ->
            val userQuery = myRef.orderByChild("userID").equalTo(userId)
            userQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val userInfo = snapshot.getValue(Model::class.java)
                        if (userInfo != null) {
                            // Update UI with user info
                            name.setText(userInfo.name)
                        } else {
                            // Handle case when user info is null
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Toast.makeText(this@homePage, "Failed to read value", Toast.LENGTH_LONG).show()
                }
            })
        }

        val myArr=ArrayList<Model>()
        var db= com.google.firebase.Firebase.database.getReference("userInfo")
        db.addChildEventListener(object : com.google.firebase.database.ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var model=snapshot.getValue(Model::class.java)
                myArr.add(model!!)
                val adapter= mentorAdapter(myArr,this@homePage)
                top_mentor_rv.layoutManager= LinearLayoutManager(this@homePage, LinearLayoutManager.HORIZONTAL, false)
                top_mentor_rv.adapter=adapter
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        logout.setOnClickListener {
            var mAuth=FirebaseAuth.getInstance()
            mAuth.signOut()
            Toast.makeText(this,"Logged Out",Toast.LENGTH_LONG).show()
            startActivity(
                Intent(this,login::class.java)
            )
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
        myProfile.setOnClickListener{
            startActivity(
                Intent(this,profile::class.java)
            );
        }



    }
}