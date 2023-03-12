package com.marcor.proyecto

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class IngresoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_ingreso, null)

        val fechaTextView = view.findViewById<TextView>(R.id.ingreso_f)

        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        fechaTextView.text = fechaActual

        val cantidadEditText = view.findViewById<EditText>(R.id.gasto_cantidad)
        val descripcionEditText = view.findViewById<EditText>(R.id.gasto_descripcion)
        val agregarButton = view.findViewById<Button>(R.id.gasto_agregar)
        val cancelarButton = view.findViewById<Button>(R.id.gasto_cancelar)

        agregarButton.setOnClickListener {
            val cantidadString = cantidadEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()

            if (cantidadString.isNotEmpty() && descripcion.isNotEmpty()) {
                try {
                    val cantidad = cantidadString.toDouble()

                    val intent = Intent()
                    intent.putExtra("cantidad", cantidad)
                    intent.putExtra("descripcion", descripcion)

                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

                    dismiss()
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "Ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Ingrese una cantidad y una descripción", Toast.LENGTH_SHORT).show()
            }
        }


        cancelarButton.setOnClickListener {

            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}

