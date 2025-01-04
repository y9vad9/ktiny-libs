package com.y9vad9.ktiny.kotlidator

public data class ValidationException(
    val failure: CreationFailure,
) : Exception("The following validation constraint has failed: ${failure.message}")