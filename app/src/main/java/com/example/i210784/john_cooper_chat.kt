package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class john_cooper_chat : AppCompatActivity() {
    private lateinit var messageEditText: EditText
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var senderId: String
    private lateinit var receiverId: String

    private lateinit var sender_tv: TextView
    private lateinit var receiver_tv: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_john_cooper_chat)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var send=findViewById<ImageView>(R.id.send)
       // sender_tv=findViewById(R.id.sender_tv)
        //receiver_tv=findViewById(R.id.receiver_tv)
         var rv=findViewById<RecyclerView>(R.id.recyclerView)


        messageEditText = findViewById(R.id.messageEditText)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        senderId = auth.currentUser?.uid ?: ""
        receiverId = "2ZnTIkqKEfYsng8lFIcwK2Uzhuh2" // Replace with receiver's user ID

        val list=ArrayList<Message>()

        list.add(Message("1","Hello",getCurrentDateTime()))
        list.add(Message("2","Hi",getCurrentDateTime()))
        list.add(Message("1","How are you",getCurrentDateTime()))
        list.add(Message("2","I am fine",getCurrentDateTime()))

        list.add(Message("1","Hello",getCurrentDateTime()))
        list.add(Message("2","Hi",getCurrentDateTime()))
        list.add(Message("1","How are you",getCurrentDateTime()))
        list.add(Message("2","I am fine",getCurrentDateTime()))
        list.add(Message("1","Hello",getCurrentDateTime()))
        list.add(Message("2","Hi",getCurrentDateTime()))
        list.add(Message("1","How are you",getCurrentDateTime()))
        list.add(Message("2","I am fine",getCurrentDateTime()))



        val adapter=MessageAdapter(list,this@john_cooper_chat)
        rv.layoutManager= LinearLayoutManager(this@john_cooper_chat)
        rv.adapter=adapter




//        send.setOnClickListener {
//
//            sendMessage(messageEditText.text.toString())
//            messageEditText.text.clear()
//        }

        send.setOnClickListener {
            var message=Message("1",messageEditText.text.toString(),getCurrentDateTime().toString())
            var db= Firebase.database.getReference("messages")
            db.push().setValue(message)
                .addOnSuccessListener {
                    messageEditText.text.clear()
                   // finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed to send Message", Toast.LENGTH_LONG).show()
                }
        }

        var db= com.google.firebase.Firebase.database.getReference("messages")
        db.addChildEventListener(object : com.google.firebase.database.ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var model=snapshot.getValue(Message::class.java)
                list.add(model!!)
                val adapter=MessageAdapter(list,this@john_cooper_chat)
                rv.layoutManager= LinearLayoutManager(this@john_cooper_chat)
                rv.adapter=adapter
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


    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }

    private fun sendMessage(message: String) {
        val messageId = database.push().key ?: ""
        val timestamp = getCurrentDateTime()
        val messageObject = Message(senderId, message, timestamp)

        database.child("messages").child(messageId).setValue(messageObject)

    }

    private fun listenForMessages() {
        database.child("messages").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    displayMessage(message)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
        })
    }

    private fun displayMessage(message: Message) {
        if (message.senderId == senderId) {
            sender_tv.text = message.text
        } else {
            receiver_tv.text = message.text

    }

}
}