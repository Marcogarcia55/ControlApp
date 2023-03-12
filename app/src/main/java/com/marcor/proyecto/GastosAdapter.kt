package com.marcor.proyecto

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider

class GastosAdapter(
    context: Context,
    gastos: ArrayList<String>,
    private val fragment: gastos
) : ArrayAdapter<String>(context, R.layout.gasto_item, gastos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val gastosList: ArrayList<String> = gastos
    private lateinit var gastosViewModel: GastosViewModel


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
        val cantidad = partes[0].trim()
        val descripcion = partes[1].trim()
        val categoria = partes[2].trim()
        val fecha = partes[3].trim()

        holder.tvCantidad.text = cantidad
        holder.tvDescripcion.text = descripcion
        holder.tvCategoria.text = categoria
        holder.tvFecha.text = fecha
        gastosViewModel = ViewModelProvider(fragment).get(GastosViewModel::class.java)

        holder.btnEliminar.setOnClickListener {
            val cantidadEliminada = cantidad.toDouble()
            gastosList.removeAt(position)
            notifyDataSetChanged()
            gastosViewModel.suma -= cantidadEliminada
            gastosViewModel.ingresos += cantidad.toDouble()
            val ingresoTxt = fragment.view?.findViewById<TextView>(R.id.txt_ingresos)
            ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
            val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma2)
            sumaT?.text = "Suma de ingresos: ${gastosViewModel.suma}"

        }
        holder.btnEditar.setOnClickListener {
            val ingreso = gastosList[position]
            val partes = ingreso.split("-")
            val cantidad = partes[0].trim()
            val descripcion = partes[1].trim()
            val categoria = partes[2].trim()
            val fecha = partes[3].trim()

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_editar_gasto)
            dialog.findViewById<EditText>(R.id.gasto_cantidad2).setText(cantidad)
            dialog.findViewById<EditText>(R.id.gasto_descripcion2).setText(descripcion)
            dialog.findViewById<Spinner>(R.id.spinner_categoria)
                .setSelection(getCategoriaIndex(categoria))
            dialog.findViewById<EditText>(R.id.ga_fecha).setText(fecha)

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
                    var j = gastosViewModel.ingresos
                    val ingresoTxt = fragment.view?.findViewById<TextView>(R.id.txt_ingresos)
                    ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma2)
                    sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"

                    gastosList[position] =
                        "$nuevaCantidad - $nuevaDescripcion - $nuevaCategoria - $nuevaFecha"
                    notifyDataSetChanged()
                    dialog.dismiss()


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
}