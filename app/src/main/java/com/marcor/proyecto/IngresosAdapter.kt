package com.marcor.proyecto

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.swagger.client.apis.UsuarioDescriptionApi
import io.swagger.client.models.UsuarioDto2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class IngresosAdapter(context: Context, ingresos: ArrayList<String>, private val fragment: ingresos) :
    ArrayAdapter<String>(context, R.layout.ingreso_item, ingresos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val ingresosList: ArrayList<String> = ingresos
    private lateinit var ingresosViewModel: IngresosViewModel
    val baseUrl = ApiService.getBaseUrl()
    val baseUrl2 = ApiService.getBaseUrl2()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.ingreso_item, parent, false)
            holder = ViewHolder()
            holder.tvCantidad = view.findViewById(R.id.tv_cantidad)
            holder.tvDescripcion = view.findViewById(R.id.tv_descripcion)
            holder.tvFecha = view.findViewById(R.id.tv_fecha)
            holder.btnEliminar = view.findViewById(R.id.btn_eliminar)
            holder.btnEditar = view.findViewById(R.id.btn_editar)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val ingreso = ingresosList[position]
        val partes = ingreso.split("-")
        val id = partes[0].trim()
        val cantidad = partes[1].trim()
        val descripcion = partes[2].trim()
        val fecha = partes[3].trim()

        holder.tvCantidad.text = cantidad
        holder.tvDescripcion.text = descripcion
        holder.tvFecha.text = fecha
        ingresosViewModel = ViewModelProvider(fragment).get(IngresosViewModel::class.java)


        holder.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Está seguro de que desea eliminar este elemento?")
                .setPositiveButton("Sí") { _, _ ->
                    val queue = Volley.newRequestQueue(context)

                    val cantidadEliminada = cantidad.toDouble()
                    ingresosList.removeAt(position)
                    notifyDataSetChanged()

                    val url = "$baseUrl/IngresoDescription/$id"
                    val progressDialog = ProgressDialog.show(context, "", "Eliminando...", true)
                    progressDialog.setCancelable(false) // Evita que el usuario cierre el ProgressDialog manualmente

                    val request = object : StringRequest(
                        Method.DELETE,
                        url,
                        { response ->

                        },
                        { error ->
                            progressDialog.dismiss() // Cierra el ProgressDialog en caso de error
                            // Manejar el error
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    ) {}

                    queue.add(request)

                    val activity2 = context as MainActivity

                    val id2 = activity2.obtenerId()
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
                    ingresosViewModel.suma -= cantidadEliminada
                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma)
                    sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"
                    progressDialog.dismiss()
                }
                .setNegativeButton("No", null)
                .show()

        }



        holder.btnEditar.setOnClickListener {


            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_editar_ingreso)
            dialog.findViewById<EditText>(R.id.gasto_cantidad).setText(cantidad)
            dialog.findViewById<EditText>(R.id.gasto_descripcion).setText(descripcion)
            dialog.findViewById<EditText>(R.id.et_fecha).setText(fecha)




            dialog.findViewById<Button>(R.id.gasto_guardar).setOnClickListener {
                val nuevaCantidad = dialog.findViewById<EditText>(R.id.gasto_cantidad).text.toString()
                val nuevaDescripcion = dialog.findViewById<EditText>(R.id.gasto_descripcion).text.toString()
                val nuevaFecha = dialog.findViewById<EditText>(R.id.et_fecha).text.toString()
                if (nuevaCantidad.isEmpty() || nuevaDescripcion.isEmpty() || nuevaFecha.isEmpty()) {
                    Toast.makeText(context, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                } else {
                    ingresosViewModel.suma -= cantidad.toDouble()
                    ingresosViewModel.suma += nuevaCantidad.toDouble()

                    val activity2 = context as MainActivity
                    val id2 = activity2.obtenerId()

                    val activity4 = context as MainActivity
                    val name = activity4.obtenerName()

                    val activity5 = context as MainActivity
                    val pass = activity5.obtenerPass()





                    val queue2 = Volley.newRequestQueue(context)
                    val url2 = "$baseUrl/IngresoDescription"


                    val jsonBody2 = JSONObject()
                    jsonBody2.put("id", id)
                    jsonBody2.put("idUsuario", id2)
                    jsonBody2.put("cantidad", nuevaCantidad.toDouble())
                    jsonBody2.put("descripcion", nuevaDescripcion)
                    jsonBody2.put("fecha", nuevaFecha)
                    Toast.makeText(context, id+" "+"idUser "+id2, Toast.LENGTH_SHORT).show()

                    val request2 = JsonObjectRequest(
                        Request.Method.PUT,
                        url2,
                        jsonBody2,
                        { response ->
                            // Manejar la respuesta exitosa

                        },
                        { error ->
                            // Manejar el error
                            val responseCode = error.networkResponse?.statusCode
                            val responseBody = String(error.networkResponse?.data ?: ByteArray(0))
                            Log.e("MyVolleyRequest", "Error al editar el ingreso: $responseBody, código de respuesta: $responseCode")
                        }

                    )

                    queue2.add(request2)

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

                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma)
                    sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"
                    val nuevoIngreso = "$id - $nuevaCantidad - $nuevaDescripcion - $nuevaFecha"
                    ingresosList[position] = nuevoIngreso

                    notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }



        return view!!
    }

    class ViewHolder {
        lateinit var tvCantidad: TextView
        lateinit var tvDescripcion: TextView
        lateinit var tvFecha: TextView
        lateinit var btnEliminar: Button
        lateinit var btnEditar: Button

    }
}