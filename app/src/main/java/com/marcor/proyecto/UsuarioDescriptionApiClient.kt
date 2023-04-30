package com.marcor.proyecto

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuarioDescriptionApiClient {
    private val service: UsuarioDescriptionService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.4:7064/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(UsuarioDescriptionService::class.java)
    }

    suspend fun getAll(): Call<List<UsuarioDescription>> {
        return service.getAll()
    }

    suspend fun getById(id: Int): Call<UsuarioDescription> {
        return service.getById(id)
    }

    suspend fun create(usuario: UsuarioDescription): Call<UsuarioDescription> {
        return service.create(usuario)
    }

    suspend fun update(id: Int, usuario: UsuarioDescription): Call<UsuarioDescription> {
        return service.update(id, usuario)
    }

    suspend fun delete(id: Int): Call<Void> {
        return service.delete(id)
    }
}
