package com.marcor.proyecto
import androidx.lifecycle.ViewModel

class GastosViewModel : ViewModel() {
    var gastosList: ArrayList<String> = ArrayList()
    var suma: Double = 0.0
    var ingresos: Double = 0.0
}
