package com.y9vad9.ktiny.kotlidator.rule

import com.y9vad9.ktiny.kotlidator.CreationFailure
import com.y9vad9.ktiny.kotlidator.ValidationResult

public class PatternValidationRule(
    private val regex: Regex,
) : ValidationRule<String> {
    override fun validate(value: String): ValidationResult {
        return if (value.matches(regex))
            ValidationResult.valid()
        else ValidationResult.invalid(Failure(regex))
    }

    /**
     * Represents a creation failure due to a pattern constraint.
     */
    public data class Failure(public val regex: Regex) : CreationFailure {
        override val message: String = "Input should match $regex"
    }
}

public fun ValidationRule.Companion.matchesPattern(regex: Regex): PatternValidationRule {
    return PatternValidationRule(regex)
}