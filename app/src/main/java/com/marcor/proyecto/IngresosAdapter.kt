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

class IngresosAdapter(context: Context, ingresos: ArrayList<String>, private val fragment: ingresos) :
    ArrayAdapter<String>(context, R.layout.ingreso_item, ingresos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val ingresosList: ArrayList<String> = ingresos
    private lateinit var ingresosViewModel: IngresosViewModel

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
        val cantidad = partes[0].trim()
        val descripcion = partes[1].trim()
        val fecha = partes[2].trim()

        holder.tvCantidad.text = cantidad
        holder.tvDescripcion.text = descripcion
        holder.tvFecha.text = fecha
        ingresosViewModel = ViewModelProvider(fragment).get(IngresosViewModel::class.java)


        holder.btnEliminar.setOnClickListener {
            val cantidadEliminada = cantidad.toDouble()
            ingresosList.removeAt(position)
            notifyDataSetChanged()
            ingresosViewModel.suma -= cantidadEliminada
            val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma)
            sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"
        }
        holder.btnEditar.setOnClickListener {
            val ingreso = ingresosList[position]
            val partes = ingreso.split("-")
            val cantidad = partes[0].trim()
            val descripcion = partes[1].trim()
            val fecha = partes[2].trim()

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
                    val sumaT = fragment.view?.findViewById<TextView>(R.id.txt_suma)
                    sumaT?.text = "Suma de ingresos: ${ingresosViewModel.suma}"
                    val nuevoIngreso = "$nuevaCantidad - $nuevaDescripcion - $nuevaFecha"
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