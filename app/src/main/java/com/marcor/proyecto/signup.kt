package com.marcor.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import io.swagger.client.apis.GastoDescriptionApi
import io.swagger.client.models.GastoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class signup : AppCompatActivity() {
    private lateinit var ingresosViewModel: IngresosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val baseUrl = ApiService.getBaseUrl()

        val registrarButton = findViewById<Button>(R.id.btnRegistrar)
        val nameTxt = findViewById<EditText>(R.id.editTextTextPersonName4)
        val passTxt = findViewById<EditText>(R.id.editTextTextPersonName2)
        val CpassTxt = findViewById<EditText>(R.id.editTextTextPersonNameC)

        val textIma = findViewById<EditText>(R.id.editTextTextPersonName5)

        val image = findViewById<ImageView>(R.id.imageView2)
        ingresosViewModel = ViewModelProvider(this).get(IngresosViewModel::class.java)
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/4305/4305692.png").into(image)

        // Agregar el evento para que cambie la imagen cuando se escriba un nuevo texto en el EditText
        textIma.addTextChangedListener {
            if (!textIma.text.isNullOrBlank()) {
                Picasso.get().load(textIma.text.toString()).into(image)
            }
        }

        registrarButton.setOnClickListener {
            val pass = passTxt.text.toString()
            val cPass = CpassTxt.text.toString()

            if (pass == cPass) {
                val queue = Volley.newRequestQueue(this)
                val jsonBody = JSONObject()
                jsonBody.put("isDeleted", true)
                jsonBody.put("createdBy", "string")
                jsonBody.put("createdDate", "2023-03-13T00:43:56.692Z")
                jsonBody.put("updateBy", "string")
                jsonBody.put("updateDate", "2023-03-13T00:43:56.692Z")
                jsonBody.put("name", nameTxt.text)
                jsonBody.put("password", pass)
                jsonBody.put("total", 0)
                jsonBody.put("imagePath", textIma.text.toString())


                val url = "$baseUrl/UsuarioDescription"
                val request = object : JsonObjectRequest(
                    Method.POST,
                    url,
                    jsonBody,
                    { response ->
                        // Manejar la respuesta exitosa aquí
                        Toast.makeText(this, "Usuario Agregado", Toast.LENGTH_SHORT).show()
                    },
                    { error ->
                        // Manejar el error aquí
                    }
                ) {}

                queue.add(request)

                nameTxt.setText("")
                passTxt.setText("")
                CpassTxt.setText("")
                textIma.setText("")
                Picasso.get().load("https://cdn-icons-png.flaticon.com/512/4305/4305692.png").into(image)

            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
