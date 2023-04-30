package com.marcor.proyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [LoginUser.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginUser : Fragment() {
    // TODO: Rename and change types of parameters
    val baseUrl = ApiService.getBaseUrl()

    val url = "$baseUrl/UsuarioDescription"
    val usuariosList = ArrayList<String>()
    var idUsuario = 0
    var EXTRA_ID = "EXTRA_ID"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login_user, container, false)

        // Inflate the layout for this fragment
        val loginButton = view.findViewById<Button>(R.id.button_login)
        val txtUser = view.findViewById<EditText>(R.id.txt_User)
        val txtPass = view.findViewById<EditText>(R.id.txt_Password)


        val queue = Volley.newRequestQueue(context)

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
                    val usuariosString = "$idUsuario - $name - $password - $total"
                    usuariosList.add(usuariosString)
                    val nuevoUsuario = Login.Companion.Usuario(idUsuario, name, password)
                    Login.usuarios[nuevoUsuario.nombre] = nuevoUsuario


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
                Toast.makeText(context, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuarios[user]?.contraseña == pass) {
                // Si el usuario y la contraseña son correctos, redirigir al usuario a la actividad principal
                val usuario = Login.Companion.Usuario(idUsuario, user, pass)

                val result = usuarios[user]?.id.toString()
                setFragmentResult("requestKey", bundleOf("bundleKey10" to result))


            } else {
                // Si el usuario y la contraseña son incorrectos, mostrar un mensaje de error
                Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
        return inflater.inflate(R.layout.fragment_login_user, container, false)
    }

    companion object {
        data class Usuario(val id: Int, val nombre: String, val contraseña: String)
        val usuarios = mutableMapOf<String, Usuario>()
    }
}