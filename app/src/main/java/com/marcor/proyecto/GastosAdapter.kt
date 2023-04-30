package com.marcor.proyecto

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
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

import org.json.JSONObject

class GastosAdapter(
    context: Context,
    gastos: ArrayList<String>,
    private val fragment: gastos
) : ArrayAdapter<String>(context, R.layout.gasto_item, gastos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val gastosList: ArrayList<String> = gastos
    private lateinit var gastosViewModel: GastosViewModel
    val baseUrl = ApiService.getBaseUrl()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.gasto_item, parent, false)
            holder = ViewHolder()
            holder.tvCantidad = view.findViewById(R.id.tv_cantidad)
            holder.tvDescripcion = view.findViewById(R.id.tv_descripcion)
            holder.tvCategoria = view.findViewById(R.id.tv_categoria)
            holder.tvFecha = view.findViewById(R.id.tv_fecha)
            holder.btnEliminar = view.findViewById(R.id.btn_eliminar2)
            holder.btnEditar = view.findViewById(R.id.btn_editar2)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }


        val ingreso = gastosList[position]
        val partes = ingreso.split("-")
        val id = partes[0].trim()
        val cantidad = partes[1].trim()
        val descripcion = partes[2].trim()
        val categoria = partes[3].trim()
        val fecha = partes[4].trim()

        holder.tvCantidad.text = cantidad
        holder.tvDescripcion.text = descripcion
        holder.tvCategoria.text = categoria
        holder.tvFecha.text = fecha
        gastosViewModel = ViewModelProvider(fragment).get(GastosViewModel::class.java)

        holder.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("¿Está seguro de que desea eliminar este elemento?")
                .setPositiveButton("Sí") { _, _ ->
                    val queue = Volley.newRequestQueue(context)

                    val cantidadEliminada = cantidad.toDouble()
                    gastosList.removeAt(position)
                    notifyDataSetChanged()
                    val url = "$baseUrl/GastoDescription/$id"

                    val progressDialog = ProgressDialog.show(context, "", "Eliminando...", true)
                    progressDialog.setCancelable(false)
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
                    gastosViewModel.suma -= cantidadEliminada
                    gastosViewModel.ingresos += cantidad.toDouble()
                    val ingresoTxt = fragment.view?.findViewById<TextView>(R.id.txt_ingresos)
                    ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma2)
                    sumaT?.text = "Suma de ingresos: ${gastosViewModel.suma}"
                    progressDialog.dismiss()
                }
                .setNegativeButton("No", null)
                .show()

        }
        holder.btnEditar.setOnClickListener {

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_editar_gasto)
            dialog.findViewById<EditText>(R.id.gasto_cantidad2).setText(cantidad)
            dialog.findViewById<EditText>(R.id.gasto_descripcion2).setText(descripcion)

            val spinnerCategoria = dialog.findViewById<Spinner>(R.id.spinner_categoria)

            // Obtener la posición de la categoría actualmente seleccionada
            val categoriaPosition = (spinnerCategoria.adapter as ArrayAdapter<String>).getPosition(categoria)

            // Establecer la selección del Spinner a la posición de la categoría
            spinnerCategoria.setSelection(categoriaPosition)

            dialog.findViewById<EditText>(R.id.ga_fecha).setText(fecha)
            print(categoria)
            dialog.findViewById<Button>(R.id.btn_guardar_gasto).setOnClickListener {
                val nuevaCantidad =
                    dialog.findViewById<EditText>(R.id.gasto_cantidad2).text.toString()
                val nuevaDescripcion =
                    dialog.findViewById<EditText>(R.id.gasto_descripcion2).text.toString()
                val nuevaCategoria =
                    dialog.findViewById<Spinner>(R.id.spinner_categoria).selectedItem.toString()
                val nuevaFecha = dialog.findViewById<EditText>(R.id.ga_fecha).text.toString()
                if (nuevaCantidad.isEmpty() || nuevaDescripcion.isEmpty() || nuevaFecha.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Por favor complete todos los campos obligatorios",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    gastosViewModel.suma -= cantidad.toDouble()

                    gastosViewModel.suma += nuevaCantidad.toDouble()
                    gastosViewModel.ingresos += cantidad.toDouble()
                    gastosViewModel.ingresos -= nuevaCantidad.toDouble()

                    val queue2 = Volley.newRequestQueue(context)
                    val url2 = "$baseUrl/GastoDescription"

                    val activity2 = context as MainActivity
                    val id2 = activity2.obtenerId()

                    val jsonBody2 = JSONObject()
                    jsonBody2.put("id", id)
                    jsonBody2.put("isDeleted", false)
                    jsonBody2.put("createdBy", "John Doe")
                    jsonBody2.put("createdDate", "2022-03-10")
                    jsonBody2.put("updateBy", "Jane Smith")
                    jsonBody2.put("updateDate", "2022-03-11")
                    jsonBody2.put("idUsuario", id2)
                    jsonBody2.put("cantidad", nuevaCantidad.toDouble())
                    jsonBody2.put("descripcion", nuevaDescripcion)
                    jsonBody2.put("categoria", nuevaCategoria)
                    jsonBody2.put("fecha", nuevaFecha)


                    val request2 = JsonObjectRequest(
                        Request.Method.PUT,
                        url2,
                        jsonBody2,
                        { response ->
                            // Manejar la respuesta exitosa
                            Toast.makeText(context, id+" "+"idUser "+id2, Toast.LENGTH_SHORT).show()
                        },
                        { error ->
                            // Manejar el error
                            val responseCode = error.networkResponse?.statusCode
                            val responseBody = String(error.networkResponse?.data ?: ByteArray(0))
                            Log.e("MyVolleyRequest", "Error al editar el ingreso: $responseBody, código de respuesta: $responseCode")
                        }

                    )

                    queue2.add(request2)

                    var j = gastosViewModel.ingresos
                    val ingresoTxt = fragment.view?.findViewById<TextView>(R.id.txt_ingresos)
                    ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma2)
                    sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"

                    gastosList[position] =
                        "$id -$nuevaCantidad - $nuevaDescripcion - $nuevaCategoria - $nuevaFecha"
                    notifyDataSetChanged()
                    dialog.dismiss()

                    setTotal()



                }
            }

            dialog.show()
        }

        return view!!
    }

    private fun getCategoriaIndex(categoria: String): Int {
        return when (categoria) {
            "Alimentación" -> 0
            "Transporte" -> 1
            "Entretenimiento" -> 2
            "Salud" -> 3
            "Educación" -> 4
            "Otros" -> 5
            else -> 0
        }
    }

    class ViewHolder {
        lateinit var tvCantidad: TextView
        lateinit var tvDescripcion: TextView
        lateinit var tvCategoria: TextView
        lateinit var tvFecha: TextView
        lateinit var btnEliminar: Button
        lateinit var btnEditar: Button
    }

    private fun setTotal(){
        val activity2 = context as MainActivity
        val id2 = activity2.obtenerId()

        val activity4 = context as MainActivity
        val name = activity4.obtenerName()

        val activity5 = context as MainActivity
        val pass = activity5.obtenerPass()

        val queue = Volley.newRequestQueue(context)
        val jsonBody = JSONObject()
        jsonBody.put("id", id2)
        jsonBody.put("name", name)
        jsonBody.put("password", pass)
        jsonBody.put("total", gastosViewModel.ingresos)

        val url = "$baseUrl/UsuarioDescription"
        val request = object : JsonObjectRequest(
            Method.PUT,
            url,
            jsonBody,
            { response ->
                // Manejar la respuesta exitosa aquí
            },
            { error ->
                // Manejar el error aquí
            }
        ) {}

        queue.add(request)
    }
}