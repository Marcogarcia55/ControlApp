package com.marcor.proyecto

import android.os.AsyncTask
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.swagger.client.apis.IngresoDescriptionApi

class MyAsyncTask : AsyncTask<Void, Void, String>() {
    private lateinit var ingresoViewModel: IngresosViewModel


    override fun doInBackground(vararg params: Void?): String {

        val apiInstance = IngresoDescriptionApi("https://9ca6-2806-2f0-6020-233f-2095-6665-8c29-ba0d.ngrok.io ")
        val response = apiInstance.apiIngresoDescriptionGet()
        var ingresosList2 = response.data?.map { ingreso ->
            "${ingreso.cantidad} - ${ingreso.descripcion} - ${ingreso.fecha}"
        }
        for (ingresoString in ingresosList2 ?: emptyList()) {
            val partes = ingresoString.split("-")
            val cantidad = partes[0].trim()
            val descripcion = partes[1].trim()
            val fecha = partes[2].trim()

            // Hacer algo con los campos separados, por ejemplo, imprimirlos
            println("Cantidad: $cantidad, Descripción: $descripcion, Fecha: $fecha")
        }
        ingresoViewModel.ingresosList = ingresosList2 as ArrayList<String>

        return response.toString()
    }

    override fun onPostExecute(result: String) {
        println(result)
    }
}
