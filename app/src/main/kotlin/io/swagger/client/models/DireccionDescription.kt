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


/**
 * 
 * @param id 
 * @param isDeleted 
 * @param createdBy 
 * @param createdDate 
 * @param updateBy 
 * @param updateDate 
 * @param calle 
 * @param colonia 
 * @param codigoPostal 
 * @param estado 
 * @param municipio 
 */
data class DireccionDescription (

    val id: kotlin.Int? = null,
    val isDeleted: kotlin.Boolean? = null,
    val createdBy: kotlin.String? = null,
    val createdDate: java.time.LocalDateTime? = null,
    val updateBy: kotlin.String? = null,
    val updateDate: java.time.LocalDateTime? = null,
    val calle: kotlin.String? = null,
    val colonia: kotlin.String? = null,
    val codigoPostal: kotlin.String? = null,
    val estado: kotlin.String? = null,
    val municipio: kotlin.String? = null
) {
}