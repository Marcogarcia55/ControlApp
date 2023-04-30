package com.marcor.proyecto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf

class MainActivity : AppCompatActivity() {
    var id: String = ""
        private set
    var name: String = ""
        private set
    var pass: String = ""
    var imagen: String = ""
        private set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = intent.getStringExtra("EXTRA_ID").toString()
        name = intent.getStringExtra("nombre").toString()
        pass = intent.getStringExtra("pass").toString()
        imagen = intent.getStringExtra("image").toString()


    }

    fun obtenerId(): String {
        return id
    }
    fun obtenerName(): String {
        return name
    }
    fun obtenerPass(): String {
        return pass
    }
    fun obtenerImage(): String {
        return imagen
    }
}
