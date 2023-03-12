package com.marcor.proyecto

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.opencsv.CSVWriter
import java.io.File

class export : Fragment() {

    private var ingresos: Float = 0.0F
    private var gastos: Float = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_export, container, false)
        setFragmentResultListener("requestKey7"){ key, bundle ->
            ingresos = bundle.getFloat("bundleKey7")
        }

        setFragmentResultListener("requestKey8"){ key, bundle ->
            gastos = bundle.getFloat("bundleKey8")
        }
        val button = view.findViewById<Button>(R.id.export_button)
        val finalizarButton = view.findViewById<Button>(R.id.button_finalizar)

        finalizarButton.setOnClickListener{
            finishAffinity(requireActivity())

        }
        button.setOnClickListener {
            val variable1 = ingresos
            val variable2 = gastos
            val fileName = "data.csv"
            val directory = requireContext().getExternalFilesDir(null)?.absolutePath

            if (variable1 != 0.0F && variable2 != 0.0F) {
                if (directory != null) {
                    val file = File(directory, fileName)
                    val filePath = file.absolutePath

                    if (!file.exists()) {
                        file.createNewFile()
                    }

                    val csvWriter = CSVWriter(File(filePath).writer())

                    csvWriter.writeNext(arrayOf("INGRESOS", "GASTOS"))
                    csvWriter.writeNext(arrayOf(variable1.toString(), variable2.toString()))

                    csvWriter.close()

                    Toast.makeText(requireContext(), " $filePath", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "No se puede acceder al directorio de archivos externos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Las variables están vacías", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
