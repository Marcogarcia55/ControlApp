package com.marcor.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import retrofit2.Call
import retrofit2.Callback
import com.android.volley.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    val baseUrl = ApiService.getBaseUrl()

    val url = "$baseUrl/UsuarioDescription"

    val usuariosList = ArrayList<String>()
    var idUsuario = 0
    var nameUser = ""
    var EXTRA_ID = "EXTRA_ID"
    var EXTRA_NAME = "EXTRA_NAME"
    var imagen = ""

    companion object {
        data class Usuario(val id: Int, val nombre: String, val contraseña: String)
        val usuarios = mutableMapOf<String, Usuario>()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        print(url)
        val loginButton = findViewById<Button>(R.id.button_login)
        val txtUser = findViewById<EditText>(R.id.txt_User)
        val txtPass = findViewById<EditText>(R.id.txt_Password)


        val queue = Volley.newRequestQueue(this)

        // Realizar una solicitud GET a la API utilizando JsonObjectRequest
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                // Manejar la respuesta exitosa
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val usuariosApi = jsonArray.getJSONObject(i)
                    idUsuario = usuariosApi.getInt("id")
                    val name = usuariosApi.getString("name").toString()
                    val password = usuariosApi.getString("password")
                    val total = usuariosApi.getDouble("total")
                    imagen = usuariosApi.getString("imagePath")

                    val usuariosString = "$idUsuario - $name - $password - $total"
                    usuariosList.add(usuariosString)
                    val nuevoUsuario = Usuario(idUsuario, name, password)
                    usuarios[nuevoUsuario.nombre] = nuevoUsuario


                }
                println("usuarios: ${usuariosList}")



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

            if (usuarios[user]?.contraseña == pass) {
                // Si el usuario y la contraseña son correctos, redirigir al usuario a la actividad principal
                val usuario = Login.Companion.Usuario(idUsuario, user, pass)
                nameUser = usuarios[user]?.nombre.toString()
                // Pasar el usuarioViewModel a la actividad principal
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(EXTRA_ID, usuarios[user]?.id.toString())
                    putExtra("nombre", usuarios[user]?.nombre.toString())
                    putExtra("pass", usuarios[user]?.contraseña.toString())
                    putExtra("image", imagen.toString())
                }
                startActivity(intent)
            } else {
                // Si el usuario y la contraseña son incorrectos, mostrar un mensaje de error
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }



    }

    fun obtenerName(): String {
        return nameUser
    }
    override fun onResume() {
        super.onResume()
        val txtUser = findViewById<EditText>(R.id.txt_User)
        val txtPass = findViewById<EditText>(R.id.txt_Password)
        txtUser.setText("")
        txtPass.setText("")
    }

}