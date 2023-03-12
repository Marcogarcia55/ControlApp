package com.marcor.proyecto

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import io.swagger.client.apis.UsuarioDescriptionApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import com.android.volley.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {


    private var isRestarted: Boolean = false
    companion object {
        const val usuario1 = "marco"
        const val password1 = "12345"

        const val usuario2 = "jair"
        const val password2 = "jair"

        const val usuario3 = "javier"
        const val password3 = "javier"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.button_login)
        val txtUser = findViewById<EditText>(R.id.txt_User)
        val txtPass = findViewById<EditText>(R.id.txt_Password)


        val queue = Volley.newRequestQueue(this)

        // Realizar una solicitud GET a la API utilizando JsonObjectRequest
        val url = "https://2595-2806-2f0-6020-233f-2095-6665-8c29-ba0d.ngrok.io/api/IngresoDescription"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                // Manejar la respuesta exitosa
                val jsonArray = response.getJSONArray("data")
                val ingresosList = ArrayList<String>()
                for (i in 0 until jsonArray.length()) {
                    val ingreso = jsonArray.getJSONObject(i)
                    val cantidad = ingreso.getInt("cantidad").toString()
                    val descripcion = ingreso.getString("descripcion")
                    val fecha = ingreso.getString("fecha")
                    val ingresoString = "$cantidad - $descripcion - $fecha"
                    ingresosList.add(ingresoString)
                }
                println("Ingresos: ${ingresosList}")
            },
            { error ->
                // Manejar el error
                Log.e("MyVolleyRequest", "Error en la solicitud de ingresos: ${error.message}")
            }
        )


        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(request)




        loginButton.setOnClickListener {
            val user = txtUser.text.toString()
            val pass = txtPass.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when {
                user == usuario1 && pass == password1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(intent)
                    val textEdit = txtUser
                    textEdit.setText("")
                    textEdit.requestFocus()

                    val pas = txtPass
                    pas.setText("")
                    onStop()

                }
                user == usuario2 && pass == password2 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(intent)
                    val textEdit = txtUser
                    textEdit.setText("")
                    textEdit.requestFocus()

                    val pas = txtPass
                    pas.setText("")
                }
                user == usuario3 && pass == password3 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(intent)
                    val textEdit = txtUser
                    textEdit.setText("")
                    textEdit.requestFocus()

                    val pas = txtPass
                    pas.setText("")
                }
                else -> {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }



}