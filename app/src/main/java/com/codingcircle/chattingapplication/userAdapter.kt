package com.codingcircle.chattingapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class userAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<userAdapter.UserView>() {



    class UserView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.txtname)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userAdapter.UserView {
        val view: View = LayoutInflater.from(context).inflate(R.layout.userlayout,parent,false)
        return UserView(view)
    }

    override fun onBindViewHolder(holder: userAdapter.UserView, position: Int) {
        val currentUser = userList[position]
        holder.name.text=currentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}