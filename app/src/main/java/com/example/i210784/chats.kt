package com.example.i210784

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database

class chats : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.add_icon)
        var chat_rv=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.chat_rv)

//        johnCooper_chat.setOnClickListener {
//            startActivity(
//                Intent(this,john_cooper_chat::class.java)
//            );
//        }
        val myArr=ArrayList<Model>()
        var db= com.google.firebase.Firebase.database.getReference("userInfo")
        db.addChildEventListener(object : com.google.firebase.database.ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var model=snapshot.getValue(Model::class.java)
                myArr.add(model!!)
                val adapter= chatAdapter(myArr,this@chats)
                chat_rv.layoutManager= LinearLayoutManager(this@chats, LinearLayoutManager.VERTICAL, false)
                chat_rv.adapter=adapter
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
}