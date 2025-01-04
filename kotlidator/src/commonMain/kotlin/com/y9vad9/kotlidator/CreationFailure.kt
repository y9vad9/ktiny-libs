package com.y9vad9.kotlidator

/**
 * Represents a failure that occurs during the creation of an object.
 */
public interface CreationFailure {
    /**
     * The error message associated with the creation failure.
     */
    public val message: String
}
