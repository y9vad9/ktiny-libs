package com.y9vad9.brawlex.foundation.validation

import com.y9vad9.kotlidator.CreationFailure

public data class ValidationException(
    val failure: CreationFailure,
) : Exception("The following validation constraint has failed: ${failure.message}")