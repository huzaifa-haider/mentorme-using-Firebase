package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class add_mentor : AppCompatActivity() {
    var isMentor:Boolean?=true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mentor)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var backArrow=findViewById<ImageView>(R.id.back_arrow)
        var upload_photo=findViewById<LinearLayout>(R.id.upload_photo_layout)
        var upload_video=findViewById<LinearLayout>(R.id.upload_video_layout)
        var uploadBtn=findViewById<TextView>(R.id.upload)
        var job=findViewById<EditText>(R.id.job)
        var company=findViewById<EditText>(R.id.company)
        var price=findViewById<EditText>(R.id.price)
        var description=findViewById<EditText>(R.id.description)
        val auth = FirebaseAuth.getInstance()
        val spinner: Spinner = findViewById(R.id.spinner)
        val options = resources.getStringArray(R.array.availability_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0) // Set to "Available"
        var status=spinner.selectedItem.toString()


        uploadBtn.setOnClickListener {
            val currentUserId = auth.currentUser?.uid
            currentUserId?.let { userId ->
                val db = Firebase.database.getReference("userInfo")
                val userQuery = db.orderByChild("userID").equalTo(userId)
                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.e("below onDataChange", "onDataChange: ")
                        for (snapshot in dataSnapshot.children) {
                            val userKey = snapshot.key
                            if (userKey != null) {
                                db.child(userKey).child("isMentor").setValue(true)
                                db.child(userKey).child("status").setValue(status)
                                db.child(userKey).child("description").setValue(description.text.toString())
                                db.child(userKey).child("job").setValue(job.text.toString())
                                db.child(userKey).child("price").setValue(price.text.toString())
                                db.child(userKey).child("company").setValue(company.text.toString())
                                    .addOnSuccessListener {
                                        Toast.makeText(this@add_mentor, "You're Successfully Added in mentor list", Toast.LENGTH_LONG).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this@add_mentor, "Failed to update", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@add_mentor, "Failed to read value", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        backArrow.setOnClickListener {
            startActivity(
                Intent(this,homePage::class.java)
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

        search.setOnClickListener {
            startActivity(
                Intent(this,search::class.java)
            );
        }

    }

}