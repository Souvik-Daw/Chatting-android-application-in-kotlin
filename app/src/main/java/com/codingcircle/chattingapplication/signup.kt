package com.codingcircle.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var pswd: EditText
    lateinit var btnSignin: Button
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        pswd = findViewById(R.id.password)
        btnSignin = findViewById(R.id.signin)
        mAuth = FirebaseAuth.getInstance()

        btnSignin.setOnClickListener{
            val email = email.text.toString()
            val pswd = pswd.text.toString()
            val name = name.text.toString()
            signUp(name,email,pswd)
        }

    }

    fun signUp(name:String,email:String,pswd:String){
        mAuth.createUserWithEmailAndPassword(email,pswd)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful)
                {
                    addUserToDatabase(name,email, mAuth.currentUser?.uid!!)
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent (this, login::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun addUserToDatabase(name:String,email:String,uid:String){
        db = FirebaseDatabase.getInstance().getReference()
        db.child("user").child(uid)
            .setValue(User(name,email,uid))

    }
}