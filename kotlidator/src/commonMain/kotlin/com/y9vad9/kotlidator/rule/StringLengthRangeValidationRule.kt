package com.y9vad9.kotlidator.rule

import com.y9vad9.brawlex.foundation.validation.rule.ValidationRule
import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

/**
 * A validation rule that ensures the length of a string is within the specified range.
 *
 * This rule validates whether the length of the given string falls within the provided [range].
 * If the length is within the range, the validation is considered successful; otherwise, it fails
 * with a [LengthRangeFailure].
 *
 * @property range The [IntRange] representing the valid length range.
 */
public data class StringLengthRangeValidationRule(
    public val range: IntRange,
) : ValidationRule<String> {

    /**
     * Validates the length of the given string against the specified [range].
     *
     * @param value The string to validate.
     * @return [ValidationResult] indicating whether the validation was successful or failed.
     */
    override fun validate(value: String): ValidationResult {
        return if (value.length in range)
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(range))
    }

    /**
     * Represents a creation failure due to a size range constraint.
     *
     * This failure occurs when the size of an input value is outside the valid range specified.
     *
     * @property range The [IntRange] that represents the valid length range.
     */
    public data class Failure(public val range: IntRange) : CreationFailure {
        override val message: String = "Length must be in range of $range"
    }
}

/**
 * Creates a [StringLengthRangeValidationRule] with the specified range.
 *
 * This function is a helper to create a validation rule that checks if the length of a string is within
 * the given [range].
 *
 * @param range The [IntRange] representing the valid length range.
 * @return A new instance of [StringLengthRangeValidationRule].
 */
public fun ValidationRule.Companion.lengthRange(range: IntRange): StringLengthRangeValidationRule {
    return StringLengthRangeValidationRule(range)
}
