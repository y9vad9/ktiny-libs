package com.y9vad9.kotlidator.rule

import com.y9vad9.brawlex.foundation.validation.rule.ValidationRule
import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

/**
 * A validation rule that ensures the length of a string is exactly the required length.
 *
 * This rule checks whether the length of the given string matches the specified [requiredLength].
 * If the length is exactly equal to the required length, the validation is considered successful;
 * otherwise, it fails with a [StringLengthExactFailure].
 *
 * @property requiredLength The exact length that the string must have.
 */
public class StringLengthValidationRule(
    private val requiredLength: Int,
) : ValidationRule<String> {

    /**
     * Validates that the length of the given string matches the required length.
     *
     * @param value The string to validate.
     * @return [ValidationResult] indicating whether the validation was successful or failed.
     */
    override fun validate(value: String): ValidationResult {
        return if (value.length == requiredLength)
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(requiredLength))
    }


    /**
     * Represents a creation failure due to an exact size constraint.
     *
     * This failure occurs when the length of an input value does not match the required length.
     *
     * @property size The required exact length.
     */
    public data class Failure(public val size: Int) : CreationFailure {
        override val message: String = "Length must be exactly $size"
    }
}

/**
 * Creates a [StringLengthValidationRule] that checks if the length of a string is exactly the specified length.
 *
 * This function is a helper to create a validation rule that ensures the length of a string matches the
 * given [requiredLength].
 *
 * @param requiredLength The exact length that the string must have.
 * @return A new instance of [StringLengthValidationRule].
 */
public fun ValidationRule.Companion.stringLengthExact(requiredLength: Int): StringLengthValidationRule {
    return StringLengthValidationRule(requiredLength)
}
