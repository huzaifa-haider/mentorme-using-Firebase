package com.example.i210784

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.util.ArrayList


class mentorAdapter(list: ArrayList<Model>, c: Context) :
    RecyclerView.Adapter<mentorAdapter.MyViewHolder>() {
    var auth = FirebaseAuth.getInstance();

    var list = list
    var context = c

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater
            .from(context)
            .inflate(R.layout.mentor_col, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


            holder.name.text=list.get(position).name.toString()
            holder.job.text=list.get(position).job.toString()
            holder.company.text="@" +list.get(position).company.toString()
            holder.session_price.text="$"+list.get(position).price.toString()+"/session"
            holder.status.text=list.get(position).status.toString()

            if(!(list.get(position).status.toString()=="Available")){
                holder.avaiable_icon.setColorFilter(ContextCompat.getColor(context, R.color.colorIconSecondary), PorterDuff.Mode.SRC_IN)
                holder.available_heart.setColorFilter(ContextCompat.getColor(context, R.color.colorIconSecondary), PorterDuff.Mode.SRC_IN)
                holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorIconSecondary))
            }

        holder.row.setOnClickListener{
            context.startActivity(
                Intent(context, johnCooper::class.java)
                    .putExtra("userID",list.get(position).userID)

            )
        }

        if(list.get(position).userID.equals(auth.currentUser?.uid.toString()) ){
            holder.row.visibility=View.GONE
            holder.row.layoutParams.width = 0
        }




    }

    class MyViewHolder: RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)

        var row = itemView.findViewById<LinearLayout>(R.id.mentor_col_layout)

        var name = itemView.findViewById<TextView>(R.id.name_tv)
        var job = itemView.findViewById<TextView>(R.id.job)
        var company=itemView.findViewById<TextView>(R.id.company)
        var session_price=itemView.findViewById<TextView>(R.id.session_price)
        var status=itemView.findViewById<TextView>(R.id.avaible_tv)
        var avaiable_icon=itemView.findViewById<ImageView>(R.id.avaiable_icon)
        var available_heart=itemView.findViewById<ImageView>(R.id.avaible_heart)





    }
}
