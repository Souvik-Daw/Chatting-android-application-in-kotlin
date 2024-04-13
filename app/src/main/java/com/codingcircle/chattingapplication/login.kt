package com.codingcircle.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    lateinit var email:EditText
    lateinit var pswd:EditText
    lateinit var btnLogin:Button
    lateinit var btnSignin:Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        email = findViewById(R.id.email)
        pswd = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)
        btnSignin = findViewById(R.id.signin)
        mAuth = FirebaseAuth.getInstance()

        btnSignin.setOnClickListener {
            val intent = Intent (this, signup::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = email.text.toString()
            val pswd = pswd.text.toString()

            login(email,pswd)
        }

    }

    fun login ( email :String, pswd : String){
        mAuth.signInWithEmailAndPassword(email,pswd)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(this, "Log in Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent (this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Log in Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
    }
}