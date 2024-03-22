package com.example.i210784

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.c
import com.squareup.picasso.Picasso
import java.util.ArrayList

class MessageAdapter(list: ArrayList<Message>, c: Context) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {


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
        holder.message.text=list.get(position).text
        holder.time.text=list.get(position).timestamp.toString()
        holder.row.setOnClickListener {
            Toast.makeText(context, "Clicked on "+list.get(position).senderId, Toast.LENGTH_SHORT).show()
        }


    }

    class MyViewHolder:RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)

        var row = itemView.findViewById<LinearLayout>(R.id.message_row_layout)
        var message = itemView.findViewById<TextView>(R.id.message_tv)
        var time = itemView.findViewById<TextView>(R.id.time_tv)
    }
}
