package com.y9vad9.brawlex.foundation.validation.rule

import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

/**
 * A validation rule that ensures the value is greater than or equal to the specified minimum value.
 *
 * This rule checks whether the given value is greater than or equal to the specified [minValue].
 * If the value meets the minimum requirement, the validation is successful; otherwise, it fails with a
 * [MinValueValidationRule.Failure] indicating the constraint violation.
 *
 * @property minValue The minimum value that the input must be greater than or equal to.
 * @param T The type of the value to be validated, constrained to types that are both `Number` and `Comparable`.
 */
public data class MinValueValidationRule<T>(
    private val minValue: T,
) : ValidationRule<T> where T : Number, T : Comparable<T> {

    /**
     * Validates that the given value is greater than or equal to the specified minimum value.
     *
     * @param value The value to validate.
     * @return [ValidationResult] indicating whether the validation was successful or failed.
     */
    override fun validate(value: T): ValidationResult {
        return if (value >= minValue)
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(value))
    }

    /**
     * Represents a creation failure due to a minimum value constraint.
     *
     * This failure occurs when the value is less than the required minimum.
     *
     * @property size The value that violated the minimum constraint.
     */
    public data class Failure<T>(
        public val size: T,
    ) : CreationFailure where T : Number, T : Comparable<T> {
        override val message: String = "Minimal value is $size"
    }
}

/**
 * Creates a [MinValueValidationRule] that checks if the value is greater than or equal to the specified minimum value.
 *
 * This function is a helper to create a validation rule that ensures the value meets the minimum constraint.
 *
 * @param min The minimum value that the input must be greater than or equal to.
 * @param T The type of the value, constrained to types that are both `Number` and `Comparable`.
 * @return A new instance of [MinValueValidationRule].
 */
public fun <T> ValidationRule.Companion.minValue(
    min: T,
): MinValueValidationRule<T> where T : Number, T : Comparable<T> {
    return MinValueValidationRule(min)
}
