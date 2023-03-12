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
import io.swagger.client.models.DireccionDescription
import io.swagger.client.models.DireccionDescriptionListResponse
import io.swagger.client.models.DireccionDescriptionResponse

import io.swagger.client.infrastructure.*

class DireccionDescriptionApi(basePath: kotlin.String = "/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return DireccionDescriptionListResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiDireccionDescriptionGet(): DireccionDescriptionListResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/DireccionDescription"
        )
        val response = request<DireccionDescriptionListResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as DireccionDescriptionListResponse
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
    fun apiDireccionDescriptionIdDelete(id: kotlin.Int): BooleanResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.DELETE,
                "/api/DireccionDescription/{id}".replace("{" + "id" + "}", "$id")
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
     * @return DireccionDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiDireccionDescriptionIdGet(id: kotlin.Int): DireccionDescriptionResponse {
        
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/DireccionDescription/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<DireccionDescriptionResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as DireccionDescriptionResponse
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
     * @return DireccionDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiDireccionDescriptionPost(body: DireccionDescription? = null): DireccionDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/api/DireccionDescription"
        )
        val response = request<DireccionDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as DireccionDescriptionResponse
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
     * @return DireccionDescriptionResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiDireccionDescriptionPut(body: DireccionDescription? = null): DireccionDescriptionResponse {
        val localVariableBody: kotlin.Any? = body
        
        val localVariableConfig = RequestConfig(
                RequestMethod.PUT,
                "/api/DireccionDescription"
        )
        val response = request<DireccionDescriptionResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as DireccionDescriptionResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}