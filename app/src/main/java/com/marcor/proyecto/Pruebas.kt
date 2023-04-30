package com.marcor.proyecto

import io.swagger.client.apis.UsuarioDescriptionApi


fun main() {
    val apiInstance = UsuarioDescriptionApi("http://localhost:7064")
    val response = apiInstance.apiUsuarioDescriptionGet()
    println(response)
}
