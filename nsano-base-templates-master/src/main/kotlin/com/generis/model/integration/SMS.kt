package com.generis.model.integration

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable


@Serializable
data class SMSRequestDto(
    @Required
    val sender: String = "",
    @Required
    val recipient: String,
    @Required
    val message: String,
    @Required
    var quoteId: String,
) {
    fun toRequestJsonString(): String {
        return "{\"sender\": \"$sender\",\"recipient\":\"$recipient\",\"message\":\"$message\"}"
    }
}

@Serializable
data class SMSId(
    val id: String
)

@Serializable
data class SMSResponseDto(
    val code: String,
    val msg: String,
    val data: SMSId? = null
)

