package com.example.i210784

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

        var name=findViewById<EditText>(R.id.nameEdit)
        var email=findViewById<EditText>(R.id.emailEdit)
        var contact=findViewById<EditText>(R.id.contactEdit)
        var country=findViewById<EditText>(R.id.countryEdit)
        var city=findViewById<EditText>(R.id.cityEdit)
        var update=findViewById<TextView>(R.id.update_profile)
        var dp=findViewById<ImageView>(R.id.profile_image)

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
                            // Update UI with user info
                            name.setText(userInfo.name)
                            email.setText(userInfo.email)
                            contact.setText(userInfo.contact)
                            country.setText(userInfo.country)
                            city.setText(userInfo.city)

                            // Load profile picture (dp) from Firebase Storage using Picasso
                            val storageRef = Firebase.storage.reference

                            val dpRef = storageRef.child(userInfo.dp)
                            val imageUrl = userInfo.dp // Assuming userInfo.dp contains the URL of the image

                            Picasso.get().load(imageUrl).into(dp)


//                            dpRef.downloadUrl.addOnSuccessListener { uri ->
//                                Toast.makeText(this@edit_profile, "Successfully fetched picture", Toast.LENGTH_LONG).show()
//                                // Load image into ImageView using Picasso
//                                Log.e("Image url", uri.toString())
//                                Picasso.get().load(uri.toString()).into(dp)
//
//                            }.addOnFailureListener {
//                                Toast.makeText(this@edit_profile, "Failed to fetch picture", Toast.LENGTH_LONG).show()
//                                // Handle failure to retrieve profile picture
//                            }
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


//        currentUserId?.let { userId ->
//            val userQuery = myRef.orderByChild("userID").equalTo(userId)
//            userQuery.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (snapshot in dataSnapshot.children) {
//                        val userInfo = snapshot.getValue(Model::class.java)
//                        if (userInfo != null) {
//                            // Update UI with user info
//                            name.setText(userInfo.name)
//                            email.setText(userInfo.email)
//                            contact.setText(userInfo.contact)
//                            country.setText(userInfo.country)
//                            city.setText(userInfo.city)
//                        } else {
//                            // Handle case when user info is null
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle error
//                    Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
//                }
//            })
//        }




        update.setOnClickListener {
            val currentUserId = auth.currentUser?.uid
            currentUserId?.let { userId ->
                val updatedModel = Model(userId, name.text.toString(), email.text.toString(), contact.text.toString(), country.text.toString(), city.text.toString(), ddp!!)
                val db = Firebase.database.getReference("userInfo")
                val userQuery = db.orderByChild("userID").equalTo(userId)

                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val userKey = snapshot.key
                            if (userKey != null) {
                                db.child(userKey).setValue(updatedModel)
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

//        // Assuming you have an ImageView with the id "profileImageView" in your layout
//
//        val database = Firebase.database
//        val auth = FirebaseAuth.getInstance()
//        val currentUserId = auth.currentUser?.uid
//        val myRef = database.getReference("userInfo")
//        currentUserId?.let { userId ->
//            val userQuery = myRef.orderByChild("userID").equalTo(userId)
//            userQuery.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (snapshot in dataSnapshot.children) {
//                        val userInfo = snapshot.getValue(Model::class.java)
//                        if (userInfo != null) {
//                            // Update UI with user info
//                            name.setText(userInfo.name)
//                            email.setText(userInfo.email)
//                            contact.setText(userInfo.contact)
//                            country.setText(userInfo.country)
//                            city.setText(userInfo.city)
//
//                            // Load profile picture (dp) from Firebase Storage using Picasso
//                            val storageRef = Firebase.storage.reference
//                            val dpRef = storageRef.child(userInfo.dp)
//
//                            dpRef.downloadUrl.addOnSuccessListener { uri ->
//                                // Load image into ImageView using Picasso
//                                Picasso.get().load(uri).into(dp)
//                            }.addOnFailureListener {
//                                // Handle failure to retrieve profile picture
//                            }
//                        } else {
//                            // Handle case when user info is null
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle error
//                    Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
//                }
//            })
//        }


//        val database = Firebase.database
//        val auth = FirebaseAuth.getInstance()
//        val currentUserId = auth.currentUser?.uid
//
//        val myRef = database.getReference("userInfo")
//
//        currentUserId?.let { userId ->
//            val userQuery = myRef.orderByChild("userID").equalTo(userId)
//            userQuery.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (snapshot in dataSnapshot.children) {
//                        val userInfo = snapshot.getValue(Model::class.java)
//                        if (userInfo != null) {
//                            // Update UI with user info
//                            name.setText(userInfo.name)
//                            email.setText(userInfo.email)
//                            contact.setText(userInfo.contact)
//                            country.setText(userInfo.country)
//                            city.setText(userInfo.city)
//                        } else {
//                            // Handle case when user info is null
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Handle error
//                    Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
//                }
//            })
//        }



//        //for data reading from database
//        val database = com.google.firebase.ktx.Firebase.database
//        val auth = FirebaseAuth.getInstance();
//        val currentuserid = auth.currentUser?.uid
//        // userid
//        //db query based on userr id
//
//
//        val myRef = database.getReference("userInfo")
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val userInfo = dataSnapshot.getValue(Model::class.java)
//                if(userIn)
//                name.setText(userInfo?.name)
//                email.setText(userInfo?.email)
//                contact.setText(userInfo?.contact)
//                country.setText(userInfo?.country)
//                city.setText(userInfo?.city)
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
//            }
//        })
//




//        update.setOnClickListener {
//            val currentUserId = auth.currentUser?.uid
//            currentUserId?.let { userId ->
//                val updatedModel = Model(imgurl.toString(),userId.toString(), name.text.toString(), email.text.toString(), contact.text.toString(), country.text.toString(), city.text.toString(), ddp!!)
//                val db = Firebase.database.getReference("userInfo")
//                val userQuery = db.orderByChild("userID").equalTo(userId)
//
//                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        for (snapshot in dataSnapshot.children) {
//                            val userKey = snapshot.key
//                            if (userKey != null) {
//                                db.child(userKey).setValue(updatedModel)
//                                    .addOnSuccessListener {
//                                        Toast.makeText(this@edit_profile, "Successfully updated", Toast.LENGTH_LONG).show()
//                                        finish()
//                                    }
//                                    .addOnFailureListener {
//                                        Toast.makeText(this@edit_profile, "Failed to update", Toast.LENGTH_LONG).show()
//                                    }
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
//                    }
//                })
//            }
//        }

//        update.setOnClickListener {
//            var model=Model(currentUserId.toString(),name.text.toString(),email.text.toString(),contact.text.toString(),country.text.toString(),city.text.toString(),ddp!!)
//            var db= Firebase.database.getReference("userInfo")
//            db.push().setValue(model)
//            //db.setValue(model)
//                .addOnSuccessListener {
//                    Toast.makeText(this,"Successfully updated",Toast.LENGTH_LONG).show()
//                    finish()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(this,"Failed to Add", Toast.LENGTH_LONG).show()
//                }
//        }



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
}