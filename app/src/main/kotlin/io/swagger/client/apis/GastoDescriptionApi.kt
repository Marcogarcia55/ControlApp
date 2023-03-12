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

import io.swagger.client.models.BooleanResponse
import io.swagger.client.models.GastoDescription
import io.swagger.client.models.GastoDescriptionListResponse
import io.swagger.client.models.GastoDescriptionResponse

import io.swagger.client.infrastructure.*

class GastoDescriptionApi(basePath: kotlin.String = "/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return GastoDescriptionListResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionGet(): GastoDescriptionListResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/GastoDescription"
        )
        val response = request<GastoDescriptionListResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDescriptionListResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param id  
     * @return BooleanResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionIdDelete(id: kotlin.Int): BooleanResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.DELETE,
                "/api/GastoDescription/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<BooleanResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as BooleanResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param id  
     * @return GastoDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionIdGet(id: kotlin.Int): GastoDescriptionResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/GastoDescription/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<GastoDescriptionResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDescriptionResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param body  (optional)
     * @return GastoDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionPost(body: GastoDescription? = null): GastoDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/api/GastoDescription"
        )
        val response = request<GastoDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDescriptionResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param body  (optional)
     * @return GastoDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionPut(body: GastoDescription? = null): GastoDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.PUT,
                "/api/GastoDescription"
        )
        val response = request<GastoDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDescriptionResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
