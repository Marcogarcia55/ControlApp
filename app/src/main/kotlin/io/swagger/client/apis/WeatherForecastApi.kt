/**
 * Control.FP.Api
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.apis

import io.swagger.client.models.WeatherForecast

import io.swagger.client.infrastructure.*

class WeatherForecastApi(basePath: kotlin.String = "/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return kotlin.Array<WeatherForecast>
     */
    @Suppress("UNCHECKED_CAST")
    fun getWeatherForecast(): kotlin.Array<WeatherForecast> {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/WeatherForecast"
        )
        val response = request<kotlin.Array<WeatherForecast>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<WeatherForecast>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
