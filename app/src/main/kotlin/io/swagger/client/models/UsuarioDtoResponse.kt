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
package io.swagger.client.models

import io.swagger.client.models.UsuarioDto

/**
 * 
 * @param &#x60;data&#x60; 
 * @param message 
 * @param errors 
 */
data class UsuarioDtoResponse (

    val `data`: UsuarioDto? = null,
    val message: kotlin.String? = null,
    val errors: kotlin.Array<kotlin.String>? = null
) {
}