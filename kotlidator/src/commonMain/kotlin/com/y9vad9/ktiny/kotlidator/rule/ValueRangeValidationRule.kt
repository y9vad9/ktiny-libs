package com.y9vad9.ktiny.kotlidator.rule

import com.y9vad9.ktiny.kotlidator.CreationFailure
import com.y9vad9.ktiny.kotlidator.ValidationResult

/**
 * A validation rule that ensures the value is within the specified range.
 *
 * This rule checks whether the given value is within the specified [range] (inclusive).
 * If the value is inside the range, the validation is successful; otherwise, it fails with a
 * [ValueRangeValidationRule.Failure] indicating the violation of the range constraint.
 *
 * @param range The range that the value must fall within (inclusive).
 * @param T The type of the value to be validated, constrained to types that are both `Number` and `Comparable`.
 */
public class ValueRangeValidationRule<T>(
    private val range: ClosedRange<T>,
) : ValidationRule<T> where T : Number, T : Comparable<T> {

    /**
     * Validates that the given value is within the specified range.
     *
     * @param value The value to validate.
     * @return [ValidationResult] indicating whether the validation was successful or failed.
     */
    override fun validate(value: T): ValidationResult {
        return if (value in range)
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(range))
    }

    /**
     * Represents a creation failure due to an invalid value range.
     *
     * This failure occurs when the value does not fall within the specified range.
     *
     * @property range The range that was violated by the value.
     */
    public data class Failure<T>(
        public val range: ClosedRange<T>,
    ) : CreationFailure where T : Number, T : Comparable<T> {
        override val message: String = "Value should be in range $range."
    }
}

/**
 * Creates a [ValueRangeValidationRule] that checks if the value is within the specified range.
 *
 * This function is a helper to create a validation rule that ensures the value is inside the range defined
 * by the provided [range] (inclusive).
 *
 * @param range The range that the input must be inside.
 * @param T The type of the value, constrained to types that are both `Number` and `Comparable`.
 * @return A new instance of [ValueRangeValidationRule].
 */
public fun <T> ValidationRule.Companion.numberValueRange(
    range: ClosedRange<T>,
): ValueRangeValidationRule<T> where T : Number, T : Comparable<T> {
    return ValueRangeValidationRule(range)
}
