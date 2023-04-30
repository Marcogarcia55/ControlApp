package com.marcor.proyecto

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

// TODO: Rename parameter arguments, choose names that match

class grafica : Fragment() {
    private lateinit var pieChart: PieChart
    private var ingresos: Float = 0.0F
    private var gastos: Float = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grafica, container, false)
        val pasar = view.findViewById<Button>(R.id.pasarF4)

        setFragmentResultListener("requestKey4"){ key, bundle ->
            ingresos = bundle.getFloat("bundleKey4")
            crearGrafica()
        }

        setFragmentResultListener("requestKey5"){ key, bundle ->
            gastos = bundle.getFloat("bundleKey5")
            crearGrafica()
        }

        pasar.setOnClickListener{
            val result = ingresos
            setFragmentResult("requestKey7", bundleOf("bundleKey7" to result))
            val result2 = gastos
            setFragmentResult("requestKey8", bundleOf("bundleKey8" to result2))
            findNavController().navigate(R.id.action_grafica_to_export, null, NavOptions.Builder().setPopUpTo(R.id.gastos2, false).build())

        }
    
        pieChart = view.findViewById(R.id.pie_chart)
        
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val result2 = ingresos
                setFragmentResult("requestKeyIN", bundleOf("bundleKeyIN" to result2))
                findNavController().navigateUp()
            }
        })
        return view
    }

    private fun crearGrafica() {
        val pieEntries = listOf(
            PieEntry(ingresos, "INGRESOS"),
            PieEntry(gastos, "GASTOS")
        )

        val dataSet = PieDataSet(pieEntries, "DISTRIBUCIÓN DE INGRESOS Y GASTOS")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()
        dataSet.setValueTextSize(20f)
        dataSet.setValueTextColor(Color.WHITE)

        val data = PieData(dataSet)
        pieChart.data = data

        pieChart.invalidate()
    }
    
    
    
}
