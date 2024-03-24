package com.example.i210784

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.currentCoroutineContext

class chatAdapter(list: ArrayList<Model>, c: Context) :
    RecyclerView.Adapter<chatAdapter.MyViewHolder>() {
    var auth = FirebaseAuth.getInstance();

    var list = list
    var context = c
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater
            .from(context)
            .inflate(R.layout.chat_row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(list.get(position).userID.equals(auth.currentUser?.uid.toString()) ){
            holder.row.visibility=View.INVISIBLE
            holder.row.layoutParams.height = 0 // Set height to 0
        }

        holder.name.text=list.get(position).name
        if(list.get(position).dp.toString()!=""){
            Picasso.get().load(list.get(position).dp).into(holder.dp)
        }

        holder.row.setOnClickListener{
            context.startActivity(
                android.content.Intent(context, john_cooper_chat::class.java)
                    .putExtra("userID",list.get(position).userID)
            )
        }


    }

    class MyViewHolder: RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
        var row = itemView.findViewById<LinearLayout>(R.id.chat_row_layout)
        var dp = itemView.findViewById<CircleImageView>(R.id.profile_image)
        var name=itemView.findViewById<TextView>(R.id.name)
        var new_message=itemView.findViewById<TextView>(R.id.new_message)

    }
}
