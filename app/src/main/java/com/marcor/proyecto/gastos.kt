package com.marcor.proyecto

import android.app.Activity
import android.app.Dialog
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
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import io.swagger.client.apis.GastoDescriptionApi
import io.swagger.client.apis.UsuarioDescriptionApi
import io.swagger.client.models.GastoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class gastos : Fragment() {

    private lateinit var gastosList: ListView
    private lateinit var gastosAdapter: ArrayAdapter<String>
    private lateinit var gastosArrayList: ArrayList<String>
    private lateinit var gastosViewModel: GastosViewModel
    val gastosList2 = ArrayList<String>()
    val baseUrl = ApiService.getBaseUrl()
    val baseUrl2 = ApiService.getBaseUrl2()

    val url = "$baseUrl/GastoDescription"

    var suma: Double = 0.0
    var ingresos1: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gastos, container, false)
        gastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)

        gastosList = view.findViewById(R.id.gastos_listview)
        val agregarButton = view.findViewById<Button>(R.id.ingresar_gastos_button2)
        val pasar = view.findViewById<Button>(R.id.pasarGrafica)
        val imagen = view.findViewById<ImageView>(R.id.imagePl)

        val activity = requireActivity() as MainActivity
        val id = activity.obtenerId()

        val api2 = UsuarioDescriptionApi(baseUrl2)
        GlobalScope.launch {
            val response = api2.apiUsuarioDescriptionIdGet(id.toInt())
            withContext(Dispatchers.Main) {
                response.data?.id.toString()
                Picasso.get().load(response.data?.imagePath.toString()).into(imagen)

            }
        }


        val url2 = "$baseUrl/GastoDescription/usuario/$id"
        gastosList2.clear()
        suma = 0.0
        val queue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(
            Request.Method.GET,
            url2,
            null,
            { response ->
                // Manejar la respuesta exitosa
                val jsonArray = response.getJSONArray("data")

                for (i in 0 until jsonArray.length()) {
                    val gasto = jsonArray.getJSONObject(i)
                    val idCantidadG = gasto.getInt("id").toString()
                    val cantidad = gasto.getDouble("cantidad").toString()
                    val descripcion = gasto.getString("descripcion")
                    val categoria = gasto.getString("categoria").toString()
                    val fecha = gasto.getString("fecha")
                    suma += cantidad.toDouble()

                    val gastoString = "$idCantidadG- $cantidad - $descripcion - $categoria- $fecha"

                    gastosList2.add(gastoString)
                }
                println("Ingresos: ${gastosList2}")

                gastosViewModel.suma = suma
                gastosArrayList  = gastosList2
                gastosAdapter  = GastosAdapter(requireContext(), gastosArrayList, this)

                gastosList.adapter  = gastosAdapter
                val sumaT = view?.findViewById<TextView>(R.id.txt_suma2)
                sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"
                gastosAdapter.notifyDataSetChanged()

                val ingresoTxt = view.findViewById<TextView>(R.id.txt_ingresos)

                setFragmentResultListener("requestKey"){ key, bundle ->
                    var result = bundle.getString("bundleKey")
                    val ingresos = result?.toDouble()?.minus(gastosViewModel.suma)
                    gastosViewModel.ingresos = ingresos.toString().toDouble()
                    ingresoTxt.text = "Suma de ingresos: $ingresos"

                }
                setFragmentResultListener("requestKeyIN"){ key, bundle ->
                    val result = bundle.getFloat("bundleKeyIN")
                    gastosViewModel.ingresos = result.toString().toDouble()
                }

            },
            { error ->
                // Manejar el error
                Log.e("MyVolleyRequest", "Error en la solicitud de ingresos: ${error.message}")
            }
        )
        val ingresoTxt = view.findViewById<TextView>(R.id.txt_ingresos)

        queue.add(request)


            ingresoTxt.text = "Suma de ingresos: ${gastosViewModel.ingresos}"




        agregarButton.setOnClickListener {
            val dialog = GastoDialog()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "IngresoDialog")
        }
        pasar.setOnClickListener{
            val result = gastosViewModel.ingresos.toFloat()
            setFragmentResult("requestKey4", bundleOf("bundleKey4" to result))
            val result2 = gastosViewModel.suma.toFloat()
            setFragmentResult("requestKey5", bundleOf("bundleKey5" to result2))

            findNavController().navigate(R.id.action_gastos2_to_grafica, null, NavOptions.Builder().setPopUpTo(R.id.gastos2, false).build())

        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val cantidad = data.getDoubleExtra("cantidad", 0.0)
            val descripcion = data.getStringExtra("descripcion")
            val categoria = data.getStringExtra("categoria")
            val fecha = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
            val activity2 = requireActivity() as MainActivity
            val idCantidadG2 = activity2.obtenerId().toInt()


            gastosViewModel.suma += cantidad
            gastosViewModel.gastosList = gastosArrayList

            gastosViewModel.ingresos -= cantidad

            val api = GastoDescriptionApi(baseUrl2)
            GlobalScope.launch(Dispatchers.IO) {
                // construye un nuevo objeto GastoDto para agregar
                val newGasto = GastoDto(
                    idUsuario = idCantidadG2,
                    cantidad = cantidad.toFloat(),
                    descripcion = descripcion,
                    categoria = categoria,
                    fecha = fecha
                )
                // realiza la solicitud POST
                val postResponse = api.apiGastoDescriptionPost(newGasto)

                // accede a los datos que devuelve la respuesta
                val gastoResponse = postResponse.data

                println("Gasto agregado con éxito: $gastoResponse")
                val gastoString = "${gastoResponse?.id}- ${gastoResponse?.cantidad} - " +
                        "${gastoResponse?.descripcion} - ${gastoResponse?.categoria}- " +
                        "${gastoResponse?.fecha}"

                activity2.runOnUiThread {
                    // actualiza la interfaz de usuario en el hilo principal
                    gastosArrayList.add(gastoString)
                    gastosAdapter.notifyDataSetChanged()
                }
            }


            var f = gastosViewModel.ingresos
            val ingresoTxt = view?.findViewById<TextView>(R.id.txt_ingresos)
            ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
            val sumaT = view?.findViewById<TextView>(R.id.txt_suma2)
            sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"



            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_guardado)
            dialog.findViewById<Button>(R.id.btn_aceptar).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}
