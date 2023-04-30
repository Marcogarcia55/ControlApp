package com.marcor.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val signButton = findViewById<Button>(R.id.btnSignUp)

        loginButton.setOnClickListener{
            val intent2 = Intent(this, Login::class.java)
            startActivity(intent2)

        }

        signButton.setOnClickListener{
            val intent2 = Intent(this, signup::class.java)
            startActivity(intent2)

        }

    }
}