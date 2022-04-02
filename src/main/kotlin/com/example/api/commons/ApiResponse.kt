package com.example.api.commons

@kotlinx.serialization.Serializable
data class ApiResponse<ResponseType>(
    val message: String,
    val data: ResponseType?
) {

    companion object {
        fun withMessage(message: String): ApiResponse<Unit> {
            return ApiResponse(data = null, message = message)
        }
    }

}
