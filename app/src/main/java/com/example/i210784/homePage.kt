package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class homePage : AppCompatActivity() {
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



        //for data reading from database
//

        val database = com.google.firebase.ktx.Firebase.database
        val myRef = database.getReference("userInfo")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(Model::class.java)
                name.setText(user?.name)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@homePage, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })

//        val database = Firebase.database
//        val myRef = database.getReference("name")
//        myRef.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val value = dataSnapshot.getValue(String::class.java)
//                name.setText(value)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@homePage, "Failed to read value", Toast.LENGTH_LONG).show()
//            }
//
//        })





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