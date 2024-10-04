package me.superangrymouse.edgetts;

/**
 * Utility class which provides simple checks and offers detailed exception messages.
 */
public final class Checks {
    /**
     * Assures an argument is not null. If the provided argument is null, then an
     * {@link IllegalArgumentException} is thrown with reason "{@code argName} is null".
     *
     * @param arg Check if this argument is null.
     * @param argName The argument's name.
     */
    public static void notNullArg(Object arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " is null");
        }
    }

    private Checks() {
        throw new UnsupportedOperationException("can not instantiate util class");
    }
}
