package com.y9vad9.kotlidator

import kotlin.jvm.JvmInline

/**
 * Represents the result of a validation operation.
 *
 * @property isValid Indicates if the validation passed (true if valid, false otherwise).
 * @property isInvalid Indicates if the validation failed (true if invalid, false otherwise).
 * @property failure The [CreationFailure] that describes why the validation failed, or `null` if valid.
 */
@JvmInline
public value class ValidationResult internal constructor(
    internal val failure: CreationFailure?,
) {
    /**
     * Returns `true` if the validation passed, or `false` otherwise.
     */
    public val isValid: Boolean get() = failure == null

    /**
     * Returns `true` if the validation failed, or `false` otherwise.
     */
    public val isInvalid: Boolean get() = failure != null

    public companion object {
        private val VALID: ValidationResult = ValidationResult(null)

        /**
         * Creates a [ValidationResult] representing a successful validation.
         *
         * @return A [ValidationResult] instance indicating validation success.
         */
        public fun valid(): ValidationResult = VALID

        /**
         * Creates a [ValidationResult] representing a failed validation.
         *
         * @param failure The [CreationFailure] that caused the validation to fail.
         * @return A [ValidationResult] instance indicating validation failure.
         */
        public fun invalid(failure: CreationFailure): ValidationResult = ValidationResult(failure)
    }
}

/**
 * Ensures that the validation result is valid, throwing an [IllegalStateException] if it is not.
 *
 * @throws IllegalStateException If the validation result is invalid.
 */
public fun ValidationResult.requireValid(): Unit = require(isValid) {
    "The validation rule $this is required to be valid, but not valid."
}

/**
 * Ensures that the validation result is invalid, throwing an [IllegalStateException] if it is valid.
 *
 * @return The [CreationFailure] describing why the validation failed.
 * @throws IllegalStateException If the validation result is valid.
 */
public fun ValidationResult.requireInvalid(): CreationFailure {
    if (isValid) error("The validation rule $this is valid.")
    return failure!!
}
