package me.superangrymouse.edgetts;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a voice.
 *
 * @see Voices
 */
public final class Voice {
    /**
     * Creates a new builder to create a voice.
     *
     * @return A builder to create a voice.
     */
    public static @NotNull Builder newBuilder() {
        return new Builder();
    }

    private final String name;
    private final String displayName;
    private final String gender;
    private final String locale;

    /**
     * Instantiates a voice that can be used for speech.
     *
     * @param name The name of the voice, also the voice's identifier.
     * @param displayName The display name or human-readable name.
     * @param gender The voice's gender.
     * @param locale The voice's locale.
     */
    public Voice(
            @NotNull String name,
            @NotNull String displayName,
            @NotNull String gender,
            @NotNull String locale
    ) {
        this.name = Checks.notNullArg(name, "name");
        this.displayName = Checks.notNullArg(displayName, "displayName");
        this.gender = Checks.notNullArg(gender, "gender");
        this.locale = Checks.notNullArg(locale, "locale");
    }

    /**
     * Gets the name.
     *
     * @return The name, also the identifier for the voice.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Gets the display name.
     *
     * @return A human-readable name.
     */
    public @NotNull String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the gender.
     *
     * @return The gender of the voice.
     */
    public @NotNull String getGender() {
        return this.gender;
    }

    /**
     * Gets the locale.
     *
     * @return The locale of the voice.
     */
    public @NotNull String getLocale() {
        return this.locale;
    }

    /**
     * A builder to simply creating {@link Voice}s.
     */
    public static final class Builder {
        private String name;
        private String displayName;
        private String gender;
        private String locale;

        private Builder() {
        }

        /**
         * Creates a voice with the builder's options.
         *
         * @return A voice with the provided options.
         */
        public Voice build() {
            return new Voice(
                    this.name,
                    this.displayName,
                    this.gender,
                    this.locale
            );
        }

        /**
         * Sets the name of the voice.
         *
         * @param name The name of the voice.
         * @return This builder.
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the display name/human-readable name of the voice.
         *
         * @param displayName The display name of the voice.
         * @return This builder.
         */
        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        /**
         * Sets the gender.
         *
         * @param gender The gender.
         * @return This builder.
         */
        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        /**
         * Sets the locale.
         *
         * @param locale The locale.
         * @return This builder.
         */
        public Builder setLocale(String locale) {
            this.locale = locale;
            return this;
        }
    }
}
