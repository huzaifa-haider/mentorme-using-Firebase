package com.example.i210784

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.FirebaseStorage

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



        //for data reading from database
        val database = com.google.firebase.ktx.Firebase.database
        val myRef = database.getReference("userInfo")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userInfo = dataSnapshot.getValue(Model::class.java)
                name.setText(userInfo?.name)
                email.setText(userInfo?.email)
                contact.setText(userInfo?.contact)
                country.setText(userInfo?.country)
                city.setText(userInfo?.city)

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@edit_profile, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })


        update.setOnClickListener {
            var model=Model(name.text.toString(),email.text.toString(),contact.text.toString(),country.text.toString(),city.text.toString(),ddp!!)
            var db= Firebase.database.getReference("userInfo")
            db.push().setValue(model)
            //db.setValue(model)
                .addOnSuccessListener {
                    Toast.makeText(this,"Successfully updated",Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed to Add", Toast.LENGTH_LONG).show()
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