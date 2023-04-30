package com.marcor.proyecto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import io.swagger.client.apis.UsuarioDescriptionApi
import io.swagger.client.models.UsuarioDto2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val baseUrl = ApiService.getBaseUrl()
    val baseUrl2 = ApiService.getBaseUrl2()

    val ingresosList2 = ArrayList<String>()
    val url = "$baseUrl/IngresoDescription"

     // ejemplo de id de usuario

    // ejemplo de id de usuario

    var suma: Double = 0.0
    var idCantidad2 = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_ingresos, container, false)
        ingresosViewModel = ViewModelProvider(this).get(IngresosViewModel::class.java)
        gastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)

        val agregarButton = view.findViewById<Button>(R.id.ingresar_gastos_button)
        val imagen = view.findViewById<ImageView>(R.id.imageP)

        val pasarButton = view.findViewById<Button>(R.id.pasarFragment)



        ingresosList = view.findViewById(R.id.ingresos_listview)

        val activity = requireActivity() as MainActivity
        val id = activity.obtenerId()
        val ima = activity.obtenerImage()


        val api2 = UsuarioDescriptionApi(baseUrl2)
        GlobalScope.launch {
            val response = api2.apiUsuarioDescriptionIdGet(id.toInt())
            withContext(Dispatchers.Main) {
                response.data?.id.toString()
                Picasso.get().load(response.data?.imagePath.toString()).into(imagen)

            }
        }


        val url2 = "$baseUrl/IngresoDescription/usuario/$id"
        print(url2)
        val queue = Volley.newRequestQueue(requireContext())
        ingresosList2.clear()
        suma = 0.0



        val request = JsonObjectRequest(
            Request.Method.GET,
            url2,
            null,
            { response ->
                // Manejar la respuesta exitosa
                val jsonArray = response.getJSONArray("data")

                for (i in 0 until jsonArray.length()) {
                        val ingreso = jsonArray.getJSONObject(i)
                        val idCantidad = ingreso.getInt("id").toString()
                        val cantidad = ingreso.getDouble("cantidad").toString()
                        val descripcion = ingreso.getString("descripcion")
                        val fecha = ingreso.getString("fecha")
                        suma += cantidad.toDouble()

                        val ingresoString = "$idCantidad - $cantidad - $descripcion - $fecha"

                        ingresosList2.add(ingresoString)
                }
                println("Ingresos: ${ingresosList2}")

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

            val activity2 = requireActivity() as MainActivity
            val id2 = activity2.obtenerId()


            ingresosViewModel.suma += cantidad



            val sumaT = view?.findViewById<TextView>(R.id.txt_suma)
            sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"

            val queue = Volley.newRequestQueue(requireContext())



            val jsonBody = JSONObject()
            jsonBody.put("idUsuario", id2)
            jsonBody.put("cantidad", cantidad)
            jsonBody.put("descripcion", descripcion)
            jsonBody.put("fecha", fecha)

            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                { response ->
                    val jsonObject = response.getJSONObject("data")
                    val idCantidad = jsonObject.getInt("id")
                    ingresosViewModel.ingresosList = ingresosArrayList
                    val ingresoString = "$idCantidad - $cantidad - $descripcion - $fecha"
                    ingresosArrayList.add(ingresoString)
                    ingresosAdapter.notifyDataSetChanged()

                },
                { error ->
                    // Manejar el error
                    val responseCode = error.networkResponse?.statusCode
                    val responseBody = String(error.networkResponse?.data ?: ByteArray(0))
                    Log.e("MyVolleyRequest", "Error al crear el ingreso: $responseBody, código de respuesta: $responseCode")
                }

            )

            queue.add(request)

            val api = UsuarioDescriptionApi(baseUrl2)
            GlobalScope.launch(Dispatchers.IO) {
                // construye un nuevo objeto GastoDto para agregar
                val newUser = UsuarioDto2(
                    id = id2.toInt(),
                    total = ingresosViewModel.suma.toDouble()
                )
                // realiza la solicitud POST
                val postResponse = api.apiUsuarioDescriptionUpdate2Put(newUser)

                // accede a los datos que devuelve la respuesta

            }


            }

    }

}
