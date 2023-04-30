package com.marcor.proyecto

object ApiService {
    private const val BASE_URL = "http://192.168.1.74:7064/api"
    private const val BASE_URL2 = "http://192.168.1.74:7064"

    fun getBaseUrl(): String {
        return BASE_URL
    }

    fun getBaseUrl2(): String {
        return BASE_URL2
    }
}