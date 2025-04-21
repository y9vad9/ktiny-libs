package com.y9vad9.ktiny.kotlidator

import com.y9vad9.ktiny.kotlidator.rule.ValidationRule

/**
 * Represents a generic constructor class for creating objects of type [TBoxed] from [TRaw].
 *
 * This class is abstract and provides a template for creating objects. Even if there's no need
 * for validation, we should follow our code-style and provide only [ValueFactory] API.
 *
 * Primary and secondary constructors should be private and use only [ValueFactory] as public API.
 *
 * @param TBoxed The type of object to be created.
 * @param TRaw The type of raw input used to create the object.
 */
public interface ValueFactory<TBoxed, TRaw> {

    /**
     * Validation rules that apply to the creation of [TBoxed] by contract of this factory.
     *
     * @see ValidationRule
     */
    public val rules: List<ValidationRule<TRaw>>

    /**
     * Creates [TBoxed] from [TRaw] in a safe way by validating
     * [rules] that are defined in the factory.
     *
     * In case of a failure, provides the first failed validation rule
     * wrapped into a [ValidationException].
     *
     * @param value The raw input value to be validated and used for object creation.
     * @return A [Result] containing the created object if successful, or an exception on failure.
     */
    public fun create(value: TRaw): Result<TBoxed>
}

/**
 * Creates a [ValueFactory] instance with validation rules and a constructor function.
 *
 * @param rules The validation rules to apply when creating the object.
 * @param constructor The function used to create an instance of [TBoxed] from [TRaw].
 * @return A [ValueFactory] with the specified rules and constructor function.
 */
public fun <TBoxed, TRaw> factory(
    rules: List<ValidationRule<TRaw>>,
    constructor: (TRaw) -> TBoxed,
): ValueFactory<TBoxed, TRaw> {
    return object : ValueFactory<TBoxed, TRaw> {
        override val rules: List<ValidationRule<TRaw>> by ::rules
        override fun create(value: TRaw): Result<TBoxed> {
            rules.forEach { rule ->
                val result = rule.validate(value)

                if (result.isInvalid)
                    return Result.failure(ValidationException(result.requireInvalid()))
            }

            return Result.success(constructor(value))
        }
    }
}

/**
 * Creates a [ValueFactory] instance with no validation rules and a constructor function.
 *
 * @param constructor The function used to create an instance of [TBoxed] from [TRaw].
 * @return A [ValueFactory] with no validation rules and the specified constructor function.
 */
public fun <TBoxed, TRaw> factory(
    constructor: (TRaw) -> TBoxed,
): ValueFactory<TBoxed, TRaw> {
    return object : ValueFactory<TBoxed, TRaw> {
        override val rules: List<ValidationRule<TRaw>> get() = emptyList()
        override fun create(value: TRaw): Result<TBoxed> {
            return Result.success(constructor(value))
        }
    }
}

/**
 * Creates an instance of the specified [TBoxed] type using the provided [TRaw].
 *
 * This function throws an exception if validation or creation fails.
 *
 * @param value The raw input value for creating the object.
 * @return The instantiated object of type [TBoxed].
 * @throws ValidationException if validation fails.
 */
public fun <TBoxed, TRaw> ValueFactory<TBoxed, TRaw>.createOrThrow(value: TRaw): TBoxed {
    return create(value).getOrThrow()
}

/**
 * Creates an instance of the specified [TBoxed] type using the provided [TRaw].
 *
 * This function returns `null` if validation or creation fails.
 *
 * @param value The raw input value for creating the object.
 * @return The instantiated object of type [TBoxed], or `null` if validation fails.
 */
public fun <TBoxed, TRaw> ValueFactory<TBoxed, TRaw>.createOrNull(value: TRaw): TBoxed? {
    return create(value).getOrNull()
}

/**
 * Validates the given input value fully against all defined validation rules.
 *
 * This method returns a list of [ValidationResult] for each validation rule,
 * which can be useful for obtaining detailed information about validation outcomes.
 *
 * @param value The raw input value to be validated.
 * @return A list of [ValidationResult] for each validation rule.
 */
public fun <TRaw> ValueFactory<*, TRaw>.check(value: TRaw): List<ValidationResult> {
    return rules.map { it.validate(value) }
}

/**
 * Creates an instance of the specified [TBoxed] type using the provided [TRaw].
 *
 * If validation fails, the specified fallback function [or] is invoked with the validation failure
 * to provide an alternative object of type [TBoxed].
 *
 * @param value The raw input value for creating the object.
 * @param or The fallback function to invoke in case of validation failure.
 * @return The instantiated object of type [TBoxed], either from successful creation or the fallback function.
 */
public fun <TBoxed, TRaw> ValueFactory<TBoxed, TRaw>.createOr(value: TRaw, or: (CreationFailure) -> TBoxed): TBoxed {
    return create(value).getOrElse {
        or((it as ValidationException).failure)
    }
}
