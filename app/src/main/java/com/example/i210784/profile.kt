package com.example.i210784

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class profile : AppCompatActivity() {
    var db_cover:String?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var edit_dp_cover=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_dp_cover)
        var editProfile=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.edit_profile)
        var dp=findViewById<CircleImageView>(R.id.profile_image)
        var cover=findViewById<ImageView>(R.id.dp_cover_frame)


        val database = Firebase.database
        val auth = FirebaseAuth.getInstance()
        val currentUserId = auth.currentUser?.uid
        val myRef = database.getReference("userInfo")



        editProfile.setOnClickListener{
            startActivity(
                Intent(this, edit_profile::class.java)
            );
        }

        var pickImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK && result.data?.data!=null){
                var img: Uri? =result.data?.data
                cover.setImageURI(img)

                var storageRef = FirebaseStorage.getInstance()
                var filename=System.currentTimeMillis().toString()+"dp.jpg"
                var ref=storageRef.getReference(filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                db_cover=it.toString()
                                currentUserId?.let { userId ->
                                    val db = Firebase.database.getReference("userInfo")
                                    val userQuery = db.orderByChild("userID").equalTo(userId)
                                    userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            for (snapshot in dataSnapshot.children) {
                                                val userKey = snapshot.key
                                                if (userKey != null) {
                                                    db.child(userKey).child("cover").setValue(db_cover)
                                                        .addOnSuccessListener {
                                                            Toast.makeText(this@profile, "Successfully updated", Toast.LENGTH_LONG).show()
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(this@profile, "Failed to update", Toast.LENGTH_LONG).show()
                                                        }
                                                }
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            Toast.makeText(this@profile, "Failed to read value", Toast.LENGTH_LONG).show()
                                        }
                                    })
                                }
                            }
                    }
            }
        }
        edit_dp_cover.setOnClickListener {
            pickImage.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }

        currentUserId?.let { userId ->
            val userQuery = myRef.orderByChild("userID").equalTo(userId)
            userQuery.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val userInfo = snapshot.getValue(Model::class.java)
                        if (userInfo != null) {
                            val imageUrl = userInfo.dp // Assuming userInfo.dp contains the URL of the image
                            if(imageUrl!=""){
                            Picasso.get().load(imageUrl).into(dp)
                            }
                            val coverUrl = userInfo.cover
                             if(coverUrl!=""){
                            Picasso.get().load(coverUrl).into(cover)
                             }
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

    }
}