package com.y9vad9.kotlidator.rule

import com.y9vad9.brawlex.foundation.validation.rule.ValidationRule
import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

/**
 * A validation rule that ensures the value is less than or equal to the specified maximum value.
 *
 * This rule checks whether the given value is less than or equal to the specified [maxValue].
 * If the value meets the maximum requirement, the validation is successful; otherwise, it fails with a
 * [MaxNumberValueValidationRule.Failure] indicating the constraint violation.
 *
 * @property maxValue The maximum value that the input must be less than or equal to.
 * @param T The type of the value to be validated, constrained to types that are both `Number` and `Comparable`.
 */
public data class MaxNumberValueValidationRule<T>(
    private val maxValue: T,
) : ValidationRule<T> where T : Number, T : Comparable<T> {

    /**
     * Validates that the given value is less than or equal to the specified maximum value.
     *
     * @param value The value to validate.
     * @return [ValidationResult] indicating whether the validation was successful or failed.
     */
    override fun validate(value: T): ValidationResult {
        return if (value <= maxValue)
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(value))
    }

    /**
     * Represents a creation failure due to a maximum value constraint.
     *
     * This failure occurs when the value exceeds the required maximum.
     *
     * @property size The value that violated the maximum constraint.
     */
    public data class Failure<T>(
        public val size: T,
    ) : CreationFailure where T : Number, T : Comparable<T> {
        override val message: String = "Maximum value is $size"
    }
}

/**
 * Creates a [MaxNumberValueValidationRule] that checks if the value is less than or equal to the specified maximum value.
 *
 * This function is a helper to create a validation rule that ensures the value meets the maximum constraint.
 *
 * @param max The maximum value that the input must be less than or equal to.
 * @param T The type of the value, constrained to types that are both `Number` and `Comparable`.
 * @return A new instance of [MaxNumberValueValidationRule].
 */
public fun <T> ValidationRule.Companion.maxNumberValue(
    max: T,
): MaxNumberValueValidationRule<T> where T : Number, T : Comparable<T> {
    return MaxNumberValueValidationRule(max)
}
