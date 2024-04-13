package com.codingcircle.chattingapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context: Context, val messageList: ArrayList<message>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemReceived = 1
    val itemSend = 2

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val sendmessage = itemView.findViewById<TextView>(R.id.sendMessage)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val receivemessage = itemView.findViewById<TextView>(R.id.receiveMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1)
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java)
        {
            val viewHolder = holder as SentViewHolder
            holder.sendmessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receivemessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return itemSend
        }
        else
        {
            return itemReceived
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}