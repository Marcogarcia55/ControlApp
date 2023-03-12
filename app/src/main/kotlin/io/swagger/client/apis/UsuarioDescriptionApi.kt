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
import io.swagger.client.models.UsuarioDescription
import io.swagger.client.models.UsuarioDescriptionListResponse
import io.swagger.client.models.UsuarioDescriptionResponse

import io.swagger.client.infrastructure.*

class UsuarioDescriptionApi(basePath: kotlin.String = "/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return UsuarioDescriptionListResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiUsuarioDescriptionGet(): UsuarioDescriptionListResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/UsuarioDescription"
        )
        val response = request<UsuarioDescriptionListResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as UsuarioDescriptionListResponse
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
    fun apiUsuarioDescriptionIdDelete(id: kotlin.Int): BooleanResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.DELETE,
                "/api/UsuarioDescription/{id}".replace("{" + "id" + "}", "$id")
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
     * @return UsuarioDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiUsuarioDescriptionIdGet(id: kotlin.Int): UsuarioDescriptionResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/UsuarioDescription/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<UsuarioDescriptionResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as UsuarioDescriptionResponse
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
     * @return UsuarioDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiUsuarioDescriptionPost(body: UsuarioDescription? = null): UsuarioDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/api/UsuarioDescription"
        )
        val response = request<UsuarioDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as UsuarioDescriptionResponse
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
     * @return UsuarioDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiUsuarioDescriptionPut(body: UsuarioDescription? = null): UsuarioDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.PUT,
                "/api/UsuarioDescription"
        )
        val response = request<UsuarioDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as UsuarioDescriptionResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
