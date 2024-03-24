package com.example.i210784

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var name=findViewById<EditText>(R.id.nameEdit)
        var email=findViewById<EditText>(R.id.emailEdit)
        var contact=findViewById<EditText>(R.id.contactEdit)
        var country=findViewById<EditText>(R.id.countryEdit)
        var city=findViewById<EditText>(R.id.cityEdit)

        var loginTextBtn=findViewById<TextView>(R.id.loginClick)
        var signUp=findViewById<Button>(R.id.signUpBtn)
        var pass=findViewById<EditText>(R.id.passEdit)
        var myAuth= FirebaseAuth.getInstance();



        signUp.setOnClickListener {

            myAuth.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString())
                .addOnSuccessListener{
                    val currentUserId = myAuth.currentUser?.uid

                    var model=Model(currentUserId.toString(),name.text.toString(),email.text.toString(),contact.text.toString(),
                        country.text.toString(),city.text.toString(),"","","","",0F,
                        "","","",false)
                    var db= Firebase.database.getReference("userInfo")
                    db.push().setValue(model)
                        //db.setValue(model)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Successfully Created",Toast.LENGTH_LONG).show()
                            //finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this,"Failed to Add", Toast.LENGTH_LONG).show()
                        }

                    startActivity(Intent(this,login::class.java))
                    finish();
                }
                .addOnFailureListener{
                    Toast.makeText(this@register, "Failed to Signup", Toast.LENGTH_SHORT).show()
                }





        }

        //hello this is new change
//        signUp.setOnClickListener {
//            myAuth.createUserWithEmailAndPassword(email.text.toString(),pass.text.toString())
//                .addOnCompleteListener{
//                    startActivity(Intent(this,homePage::class.java))
//                    finish();
//                }
//                .addOnFailureListener{
//                    Toast.makeText(this, "Failed to Signup", Toast.LENGTH_SHORT).show()
//                }
//        }

        loginTextBtn.setOnClickListener {
            startActivity(
                Intent(this,login::class.java)
            );
        }

    }
}