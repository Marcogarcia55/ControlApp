package com.marcor.proyecto

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class gastos : Fragment() {

    private lateinit var gastosList: ListView
    private lateinit var gastosAdapter: ArrayAdapter<String>
    private lateinit var gastosArrayList: ArrayList<String>
    private lateinit var gastosViewModel: GastosViewModel

    var suma: Double = 0.0
    var ingresos1: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gastos, container, false)
        gastosViewModel = ViewModelProvider(this).get(GastosViewModel::class.java)

        val agregarButton = view.findViewById<Button>(R.id.ingresar_gastos_button2)
        val pasar = view.findViewById<Button>(R.id.pasarGrafica)

        val sumaT = view?.findViewById<TextView>(R.id.txt_suma2)
        sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"
        gastosList = view.findViewById(R.id.gastos_listview)

        gastosArrayList = gastosViewModel.gastosList
        gastosAdapter = GastosAdapter(requireContext(), gastosArrayList, this)

        gastosList.adapter = gastosAdapter

        val ingresoTxt = view.findViewById<TextView>(R.id.txt_ingresos)

        setFragmentResultListener("requestKey"){ key, bundle ->
            val result = bundle.getString("bundleKey")

            ingresoTxt.text = "Suma de ingresos: $result"
            gastosViewModel.ingresos = result.toString().toDouble()
        }


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

            val gastoString = "$cantidad - $descripcion - $categoria- ($fecha)"
            gastosArrayList.add(gastoString)
            gastosAdapter.notifyDataSetChanged()

            gastosViewModel.suma += cantidad
            gastosViewModel.gastosList = gastosArrayList

            gastosViewModel.ingresos -= cantidad
            var f = gastosViewModel.ingresos
            val ingresoTxt = view?.findViewById<TextView>(R.id.txt_ingresos)
            ingresoTxt?.text = "Suma de ingresos: ${gastosViewModel.ingresos}"
            val sumaT = view?.findViewById<TextView>(R.id.txt_suma2)
            sumaT?.text = "Suma de gastos: ${gastosViewModel.suma}"

            Toast.makeText(
                requireContext(),
                "Gasto agregado: $gastoString",
                Toast.LENGTH_SHORT
            ).show()

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
