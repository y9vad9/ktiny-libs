package com.y9vad9.brawlex.foundation.validation.rule

import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

/**
 * Represents a generic validation rule that checks whether a given input value meets a specific condition.
 *
 * @param Input The type of input to validate.
 */
public interface ValidationRule<Input> {

    /**
     * Validates the specified [value] against the rule's condition.
     *
     * @param value The input value to validate.
     * @return A [ValidationRule.Result] indicating whether the validation passed or failed.
     */
    public fun validate(value: Input): ValidationResult

    public companion object;
}