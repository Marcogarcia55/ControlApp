package com.marcor.proyecto

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ingresos : Fragment() {

    private lateinit var ingresosList: ListView
    private lateinit var ingresosAdapter: ArrayAdapter<String>
    private lateinit var ingresosArrayList: ArrayList<String>
    private lateinit var ingresosViewModel: IngresosViewModel
    private lateinit var gastosViewModel: GastosViewModel
    val ingresosList2 = ArrayList<String>()
    val url = "http://192.168.1.6:7064/api/IngresoDescription"
    var suma: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingresos, container, false)
        ingresosViewModel = ViewModelProvider(this).get(IngresosViewModel::class.java)
        gastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)

        val agregarButton = view.findViewById<Button>(R.id.ingresar_gastos_button)
        val pasarButton = view.findViewById<Button>(R.id.pasarFragment)


        ingresosList = view.findViewById(R.id.ingresos_listview)


        val queue = Volley.newRequestQueue(requireContext())
        ingresosList2.clear()
        suma = 0.0
        // Realizar una solicitud GET a la API utilizando JsonObjectRequest

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                // Manejar la respuesta exitosa
                val jsonArray = response.getJSONArray("data")

                for (i in 0 until jsonArray.length()) {
                    val ingreso = jsonArray.getJSONObject(i)
                    val cantidad = ingreso.getDouble("cantidad").toString()
                    val descripcion = ingreso.getString("descripcion")
                    val fecha = ingreso.getString("fecha")
                    suma += cantidad.toDouble()

                    val ingresoString = "$cantidad - $descripcion - ($fecha)"

                    ingresosList2.add(ingresoString)

                }
                println("Ingresos: ${ingresosList2} suma: $suma")

                ingresosViewModel.suma = suma
                ingresosArrayList = ingresosList2
                ingresosAdapter = IngresosAdapter(requireContext(), ingresosArrayList, this)

                ingresosList.adapter = ingresosAdapter
                val sumaT = view?.findViewById<TextView>(R.id.txt_suma)
                sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"
                ingresosAdapter.notifyDataSetChanged()
            },
            { error ->
                // Manejar el error
                Log.e("MyVolleyRequest", "Error en la solicitud de ingresos: ${error.message}")
            }
        )

        queue.add(request)



        pasarButton.setOnClickListener {
            suma = ingresosViewModel.suma
            val result = suma.toString()
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))

            findNavController().navigate(R.id.action_ingresos_to_gastos2, null, NavOptions.Builder().setPopUpTo(R.id.gastos2, false).build())

        }

        agregarButton.setOnClickListener {
            val dialog = IngresoDialog()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "IngresoDialog")
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val cantidad = data.getDoubleExtra("cantidad", 0.0)
            val descripcion = data.getStringExtra("descripcion")
            val fecha = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())


            val ingresoString = "$cantidad - $descripcion - ($fecha)"
            ingresosArrayList.add(ingresoString)
            ingresosAdapter.notifyDataSetChanged()

            ingresosViewModel.suma += cantidad
            ingresosViewModel.ingresosList = ingresosArrayList


            val sumaT = view?.findViewById<TextView>(R.id.txt_suma)
            sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"

            val queue = Volley.newRequestQueue(requireContext())


            val jsonBody = JSONObject()
            jsonBody.put("isDeleted", false)
            jsonBody.put("createdBy", "John Doe")
            jsonBody.put("createdDate", "2022-03-10")
            jsonBody.put("updateBy", "Jane Smith")
            jsonBody.put("updateDate", "2022-03-11")
            jsonBody.put("cantidad", cantidad)
            jsonBody.put("suma", ingresosViewModel.suma)
            jsonBody.put("descripcion", descripcion)
            jsonBody.put("fecha", fecha)

            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                { response ->
                    // Manejar la respuesta exitosa
                    val ingreso = response.getJSONObject("data")
                    val cantidad = ingreso.getInt("cantidad").toString()
                    val descripcion = ingreso.getString("descripcion")
                    val fecha = ingreso.getString("fecha")
                    val ingresoString = "$cantidad - $descripcion - $fecha"
                    println("Ingreso creado: $ingresoString")
                },
                { error ->
                    // Manejar el error
                    val responseCode = error.networkResponse?.statusCode
                    val responseBody = String(error.networkResponse?.data ?: ByteArray(0))
                    Log.e("MyVolleyRequest", "Error al crear el ingreso: $responseBody, código de respuesta: $responseCode")
                }

            )

            queue.add(request)


        }

    }

}
