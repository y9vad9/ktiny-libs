package com.y9vad9.brawlex.foundation.validation.rule

import com.y9vad9.kotlidator.CreationFailure
import com.y9vad9.kotlidator.ValidationResult

public data object StringIsNotBlankRule : ValidationRule<String> {
    override fun validate(value: String): ValidationResult {
        return if (value.isNotBlank())
            ValidationResult.valid()
        else ValidationResult.invalid(Failure())
    }

    /**
     * Represents a creation failure due to a blank value constraint.
     */
    public class Failure : CreationFailure {
        override val message: String = "Provided value is empty"
    }
}

public fun ValidationRule.Companion.stringNotBlank(): StringIsNotBlankRule {
    return StringIsNotBlankRule
}