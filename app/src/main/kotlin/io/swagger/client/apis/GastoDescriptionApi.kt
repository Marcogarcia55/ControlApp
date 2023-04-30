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
import io.swagger.client.models.GastoDto
import io.swagger.client.models.GastoDtoListResponse
import io.swagger.client.models.GastoDtoResponse

import io.swagger.client.infrastructure.*

class GastoDescriptionApi(basePath: kotlin.String = "/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @return GastoDtoListResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionGet(): GastoDtoListResponse {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/GastoDescription"
        )
        val response = request<GastoDtoListResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDtoListResponse
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
     * @return GastoDtoResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionIdGet(id: kotlin.Int): GastoDtoResponse {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/GastoDescription/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<GastoDtoResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDtoResponse
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
     * @return GastoDtoResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionPost(body: GastoDto? = null): GastoDtoResponse {
        val localVariableBody: kotlin.Any? = body
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/api/GastoDescription"
        )
        val response = request<GastoDtoResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDtoResponse
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
     * @return GastoDtoResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionPut(body: GastoDto? = null): GastoDtoResponse {
        val localVariableBody: kotlin.Any? = body
        val localVariableConfig = RequestConfig(
                RequestMethod.PUT,
                "/api/GastoDescription"
        )
        val response = request<GastoDtoResponse>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDtoResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param usuarioId  
     * @return GastoDtoListResponse
     */
    @Suppress("UNCHECKED_CAST")
    fun apiGastoDescriptionUsuarioUsuarioIdGet(usuarioId: kotlin.Int): GastoDtoListResponse {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/api/GastoDescription/usuario/{usuarioId}".replace("{" + "usuarioId" + "}", "$usuarioId")
        )
        val response = request<GastoDtoListResponse>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as GastoDtoListResponse
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
