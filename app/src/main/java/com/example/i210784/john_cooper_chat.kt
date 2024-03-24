package com.example.i210784

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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



    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_john_cooper_chat)

        var search=findViewById<ImageView>(R.id.search_icon)
        var chat=findViewById<ImageView>(R.id.chat_icon)
        var home=findViewById<ImageView>(R.id.home_icon)
        var add=findViewById<CircleImageView>(R.id.add_icon)
        var send=findViewById<ImageView>(R.id.send)
        var rv=findViewById<RecyclerView>(R.id.recyclerView)
        var name=findViewById<TextView>(R.id.name)
        var userId=intent.getStringExtra("userID")
        var voice_note=findViewById<ImageView>(R.id.voice_note)
        var attatch_photo=findViewById<ImageView>(R.id.attach_photo)
        var attatch_file=findViewById<ImageView>(R.id.attach_file)

        messageEditText = findViewById(R.id.messageEditText)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference




        val database = Firebase.database
        val myRef = database.getReference("userInfo")
        val userQuery = myRef.orderByChild("userID").equalTo(userId)

        userQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val userInfo = snapshot.getValue(Model::class.java)
                    if (userInfo != null) {
                        // Update UI with user info
                        name.setText(userInfo.name.toString())

                    } else {
                        Toast.makeText(this@john_cooper_chat, "User not found", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Toast.makeText(this@john_cooper_chat, "Failed to read value", Toast.LENGTH_LONG).show()
            }
        })

        senderId = auth.currentUser?.uid ?: ""

        val list=ArrayList<Message>()

        val adapter=MessageAdapter(list,this@john_cooper_chat)
        rv.layoutManager= LinearLayoutManager(this@john_cooper_chat)
        rv.adapter=adapter


        send.setOnClickListener {
            var message=Message(senderId.toString(),userId.toString(),messageEditText.text.toString(),getCurrentDateTime().toString())
            var db= Firebase.database.getReference("messages")
            db.push().setValue(message)
                .addOnSuccessListener {
                    messageEditText.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed to send Message", Toast.LENGTH_LONG).show()
                }
        }

        var db= Firebase.database.getReference("messages")
        db.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(userId.toString().equals(snapshot.getValue(Message::class.java)?.receiverId) &&
                    senderId.toString().equals(snapshot.getValue(Message::class.java)?.senderId) ||
                    userId.toString().equals(snapshot.getValue(Message::class.java)?.senderId) &&
                    senderId.toString().equals(snapshot.getValue(Message::class.java)?.receiverId)
                    ){
                    var model=snapshot.getValue(Message::class.java)
                    list.add(model!!)
                    val adapter=MessageAdapter(list,this@john_cooper_chat)
                    rv.layoutManager= LinearLayoutManager(this@john_cooper_chat)
                    rv.adapter=adapter
                }


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
        var pickImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK && result.data?.data!=null){

                var img: Uri? =result.data?.data
                //dp.setImageURI(img)

                var storageRef = FirebaseStorage.getInstance()
                var filename=System.currentTimeMillis().toString()+"dp.jpg"
                var ref=storageRef.getReference(filename)
                ref.putFile(img!!)
                    .addOnSuccessListener {
                        ref.downloadUrl
                            .addOnSuccessListener {
                                var message=Message(senderId.toString(),userId.toString(),it.toString(),getCurrentDateTime().toString())
                                var db= Firebase.database.getReference("messages")
                                db.push().setValue(message)
                                    .addOnSuccessListener {
                                       // messageEditText.text.clear()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this,"Failed to send Message", Toast.LENGTH_LONG).show()
                                    }
                            }
                    }
            }
        }


        attatch_photo.setOnClickListener{
            pickImage.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            )
        }

        val pickVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data?.data != null) {
                val videoUri: Uri? = result.data?.data
                val storageRef = FirebaseStorage.getInstance().reference
                val filename = System.currentTimeMillis().toString() + ".mp4"
                val ref = storageRef.child(filename)

                ref.putFile(videoUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        ref.downloadUrl.addOnSuccessListener {
                            var message=Message(senderId.toString(),userId.toString(),videoUri.toString(),getCurrentDateTime().toString())
                            var db= Firebase.database.getReference("messages")
                            db.push().setValue(message)
                                .addOnSuccessListener {
                                    // messageEditText.text.clear()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this,"Failed to send Message", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                    .addOnFailureListener {

                    }
            }
        }

        attatch_file.setOnClickListener {
            pickVideo.launch(Intent(Intent.ACTION_PICK,
                MediaStore.Video.Media.INTERNAL_CONTENT_URI))
        }



    }


    private fun startRecording() {
        val fileName = "${externalCacheDir?.absolutePath}/recording.3gp"
        val mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setOutputFile(fileName)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
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