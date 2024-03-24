package com.example.i210784

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class MessageAdapter(list: ArrayList<Message>, c: Context) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
   var auth = FirebaseAuth.getInstance();

    var list = list
    var context = c
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater
            .from(context)
            .inflate(R.layout.message_row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(list.get(position).senderId.equals(auth.currentUser?.uid.toString())&&
            list.get(position).text.toString().startsWith("https://firebasestorage")){

            Picasso.get().load(list.get(position).text).into(holder.sent_image)
            holder.time.text=list.get(position).timestamp.toString()



        }
        else if(list.get(position).text.toString().startsWith("https://firebasestorage")){
                Picasso.get().load(list.get(position).text).into(holder.received_image)
                holder.received_time.text=list.get(position).timestamp.toString()

        }
        else if(list.get(position).senderId.equals(auth.currentUser?.uid.toString())&&
            list.get(position).text.toString().startsWith("https://firebasestorage") &&
            list.get(position).text.toString().endsWith(".mp4")){

            holder.sent_video.setVideoPath(list.get(position).text)
            holder.time.text=list.get(position).timestamp.toString()


        }
        else if(list.get(position).text.toString().startsWith("https://firebasestorage") &&
            list.get(position).text.toString().endsWith(".mp4")) {

            holder.received_video.setVideoURI(Uri.parse(list.get(position).text))
            holder.received_time.text=list.get(position).timestamp.toString()

        }
        else{
            if (list.get(position).senderId.equals(auth.currentUser?.uid.toString())) {

                holder.message.text=list.get(position).text
                holder.time.text=list.get(position).timestamp.toString()

            }
            else{
                holder.received_message.text=list.get(position).text
                holder.received_time.text=list.get(position).timestamp.toString()
            }
        }

        holder.sent_video.setOnClickListener{
            if(holder.sent_video.isPlaying){
                holder.sent_video.pause()
            }
            else{
                holder.sent_video.start()
            }
        }
        holder.received_video.setOnClickListener{
            if(holder.received_video.isPlaying){
                holder.received_video.pause()
            }
            else{
                holder.received_video.start()
            }
        }



    }

    class MyViewHolder:RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
        var row = itemView.findViewById<LinearLayout>(R.id.message_row_layout)
        var message = itemView.findViewById<TextView>(R.id.message_tv)
        var time = itemView.findViewById<TextView>(R.id.time_tv)
        var received_message=itemView.findViewById<TextView>(R.id.received_message_tv)
        var received_time=itemView.findViewById<TextView>(R.id.received_time_tv)
        var received_image=itemView.findViewById<ImageView>(R.id.received_image)
        var sent_image=itemView.findViewById<ImageView>(R.id.sent_image)
        var received_video=itemView.findViewById<VideoView>(R.id.received_video)
        var sent_video=itemView.findViewById<VideoView>(R.id.sent_video)

    }
}
