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
 * @param cantidad 
 * @param descripcion 
 * @param fecha 
 */
data class IngresosDescription (

    val id: kotlin.Int? = null,
    val isDeleted: kotlin.Boolean? = null,
    val createdBy: kotlin.String? = null,
    val createdDate: kotlin.String? = null,
    val updateBy: kotlin.String? = null,
    val updateDate: kotlin.String? = null,
    val cantidad: kotlin.Float? = null,
    val suma: kotlin.Float? = null,
    val descripcion: kotlin.String? = null,
    val fecha: kotlin.String? = null
) {
}