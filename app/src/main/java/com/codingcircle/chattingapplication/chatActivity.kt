package com.codingcircle.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class chatActivity : AppCompatActivity() {

    lateinit var messageRecyclerView: RecyclerView
    lateinit var messageBox : EditText
    lateinit var sendButton : ImageView
    lateinit var messageAdapter: messageAdapter
    lateinit var messageList: ArrayList<message>
    lateinit var db : DatabaseReference

    var receiverRoom:String? =null
    var senderRoom:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        db = FirebaseDatabase.getInstance().getReference()

        messageRecyclerView = findViewById(R.id.chatRecylerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sentButton)
        messageList=ArrayList()
        messageAdapter = messageAdapter(this,messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        db.child("chats").child(senderRoom!!)
            .child("messages").addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children)
                    {
                        val messages = postSnapshot.getValue(message::class.java)
                        messageList.add(messages!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendButton.setOnClickListener{

            val msg = messageBox.text.toString()
            val messageObject = message(msg,senderUid)

            db.child("chats")
                .child(senderRoom!!)
                .child("messages")
                .push().setValue(messageObject).addOnSuccessListener {
                    db.child("chats")
                        .child(receiverRoom!!)
                        .child("messages")
                        .push().setValue(messageObject)
                }

            messageBox.setText("")



        }

    }
}