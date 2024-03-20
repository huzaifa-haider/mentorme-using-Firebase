package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider

class verify : AppCompatActivity() {
    private lateinit var editTextDigit1: EditText
    private lateinit var editTextDigit2: EditText
    private lateinit var editTextDigit3: EditText
    private lateinit var editTextDigit4: EditText
    private lateinit var editTextDigit5: EditText
    private lateinit var editTextDigit6: EditText
   // @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_verify)

       var verify=findViewById<Button>(R.id.verify)
       var mAuth=FirebaseAuth.getInstance();
        editTextDigit1 = findViewById(R.id.digit_1)
        editTextDigit2 = findViewById(R.id.digit_2)
        editTextDigit3 = findViewById(R.id.digit_3)
        editTextDigit4 = findViewById(R.id.digit_4)
        editTextDigit5 = findViewById(R.id.digit_5)
        editTextDigit6 = findViewById(R.id.digit_6)
       var otp=getIntent().getStringExtra("token")
       var otpp=editTextDigit1.text.toString()+editTextDigit2.text.toString()+editTextDigit3.text.toString()+editTextDigit4.text.toString()+editTextDigit5.text.toString()+editTextDigit6.text.toString();

       verify.setOnClickListener {
           var credential= PhoneAuthProvider.getCredential(otp!!,otpp)
           var auth=FirebaseAuth.getInstance()
           auth.signInWithCredential(credential)
               .addOnSuccessListener{
                   var i= Intent(this,homePage::class.java)
                   i.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                   startActivity(i)
               }
               .addOnFailureListener{
                   Toast.makeText(this,"Failed to verify", Toast.LENGTH_LONG).show()
               }

       }


        // Request focus for the first EditText field to automatically show the keyboard
        editTextDigit1.requestFocus()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setEditTextListeners() {
        editTextDigit1.addTextChangedListener(OTPTextWatcher(editTextDigit1, editTextDigit2))
        editTextDigit2.addTextChangedListener(OTPTextWatcher(editTextDigit2, editTextDigit3))
        editTextDigit3.addTextChangedListener(OTPTextWatcher(editTextDigit3, editTextDigit4))
        editTextDigit4.addTextChangedListener(OTPTextWatcher(editTextDigit4, editTextDigit5))
        editTextDigit5.addTextChangedListener(OTPTextWatcher(editTextDigit5, editTextDigit6))
        editTextDigit6.addTextChangedListener(OTPTextWatcher(editTextDigit6, null))
    }
    private fun setEditTextBackspace(){
        editTextDigit1.addTextChangedListener(OTPTextWatcher(editTextDigit1, null))
        editTextDigit2.addTextChangedListener(OTPTextWatcher(editTextDigit2, editTextDigit1))
        editTextDigit3.addTextChangedListener(OTPTextWatcher(editTextDigit3, editTextDigit2))
        editTextDigit4.addTextChangedListener(OTPTextWatcher(editTextDigit4, editTextDigit3))
        editTextDigit5.addTextChangedListener(OTPTextWatcher(editTextDigit5, editTextDigit4))
        editTextDigit6.addTextChangedListener(OTPTextWatcher(editTextDigit6, editTextDigit5))
    }

    
    private inner class OTPTextWatcher(
        private val currentEditText: EditText,
        private val nextEditText: EditText?
    ) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) {
                nextEditText?.requestFocus() // Move focus to the next EditText field
            }
        }
    }
}