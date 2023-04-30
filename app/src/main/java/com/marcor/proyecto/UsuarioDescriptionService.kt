package com.marcor.proyecto

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UsuarioDescriptionService {
    @GET("/api/UsuarioDescription")
    suspend fun getAll(): Call<List<UsuarioDescription>>

    @GET("/api/UsuarioDescription/{id}")
    suspend fun getById(@Path("id") id: Int): Call<UsuarioDescription>

    @POST("/api/UsuarioDescription")
    suspend fun create(@Body usuario: UsuarioDescription): Call<UsuarioDescription>

    @PUT("/api/UsuarioDescription/{id}")
    suspend fun update(@Path("id") id: Int, @Body usuario: UsuarioDescription): Call<UsuarioDescription>

    @DELETE("/api/UsuarioDescription/{id}")
    suspend fun delete(@Path("id") id: Int): Call<Void>
}
