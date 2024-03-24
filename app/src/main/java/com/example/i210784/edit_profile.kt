package com.example.i210784

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class edit_profile : AppCompatActivity() {
    var ddp:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        Log.e("edit_profile", "onCreate: ")
        var name=findViewById<EditText>(R.id.nameEdit)
        var email=findViewById<EditText>(R.id.emailEdit)
        var contact=findViewById<EditText>(R.id.contactEdit)
        var country=findViewById<EditText>(R.id.countryEdit)
        var city=findViewById<EditText>(R.id.cityEdit)
        var update=findViewById<TextView>(R.id.update_profile)
        var dp=findViewById<CircleImageView>(R.id.profile_image)

        val database = Firebase.database
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
                            email.setText(userInfo.email)
                            contact.setText(userInfo.contact)
                            country.setText(userInfo.country)
                            city.setText(userInfo.city)

                            val imageUrl = userInfo.dp.toString() // Assuming userInfo.dp contains the URL of the image
                            if(imageUrl != ""){ // Add a null check for dp
                                Picasso.get().load(imageUrl).into(dp)
                            } else {
                                // If dp is not set or dp is null, you can handle it gracefully, for example, by displaying a placeholder image or a default image.
                                Toast.makeText(this@edit_profile, "Image not found", Toast.LENGTH_LONG).show()
                            }

                        } else {
                            // Handle case when user info is null
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                    Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
                }
            })
        }


        update.setOnClickListener {

            val currentUserId = auth.currentUser?.uid
            currentUserId?.let { userId ->
                val db = Firebase.database.getReference("userInfo")
                val userQuery = db.orderByChild("userID").equalTo(userId)
                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val userKey = snapshot.key
                            if (userKey != null) {
                                db.child(userKey).child("userID").setValue(userId)
                                db.child(userKey).child("name").setValue(name.text.toString())
                                db.child(userKey).child("email").setValue(email.text.toString())
                                db.child(userKey).child("contact").setValue(contact.text.toString())
                                db.child(userKey).child("country").setValue(country.text.toString())
                                db.child(userKey).child("city").setValue(city.text.toString())
                                    .addOnSuccessListener {
                                        Toast.makeText(this@edit_profile, "Successfully updated", Toast.LENGTH_LONG).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this@edit_profile, "Failed to update", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }



        var pickImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK && result.data?.data!=null){

                var img: Uri? =result.data?.data
                dp.setImageURI(img)

                var storageRef = FirebaseStorage.getInstance()
                var filename=System.currentTimeMillis().toString()+"dp.jpg"
                var ref=storageRef.getReference(filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                ddp=it.toString()
                                updateUserInfo(ddp!!)
                            }
                    }
            }
        }
        dp.setOnClickListener {
            pickImage.launch(
                Intent(
                    Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }





    }

    private fun updateUserInfo(mydp: String) {
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid
        currentUserId?.let { userId ->
            val db = Firebase.database.getReference("userInfo")
            val userQuery = db.orderByChild("userID").equalTo(userId)
            userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val userKey = snapshot.key
                        if (userKey != null) {
                            db.child(userKey).child("dp").setValue(mydp)
                                .addOnSuccessListener {
                                    Toast.makeText(this@edit_profile, "Successfully updated", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this@edit_profile, "Failed to update", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}