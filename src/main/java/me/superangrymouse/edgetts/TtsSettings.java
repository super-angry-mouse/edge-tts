package me.superangrymouse.edgetts;

import org.jetbrains.annotations.NotNull;

/**
 * Settings used to generate speech.
 */
public final class TtsSettings {
    private final String text;
    private final String format;
    private final Voice voice;
    private final ProsodySettings prosodySettings;

    /**
     * Instantiates settings used to generate speech.
     *
     * @param text The text that will be converted into speech.
     * @param format The output format.
     * @param voice The voice used in the speech.
     * @param prosodySettings The prosody settings for the voice.
     */
    public TtsSettings(
            @NotNull String text,
            @NotNull String format,
            @NotNull Voice voice,
            @NotNull ProsodySettings prosodySettings
    ) {
        this.text = Checks.notNullArg(text, "text");
        this.format = Checks.notNullArg(format, "format");
        this.voice = Checks.notNullArg(voice, "voice");
        this.prosodySettings = Checks.notNullArg(prosodySettings, "prosodySetting");
    }

    /**
     * Gets the text.
     *
     * @return The text that will be converted into speech.
     */
    public @NotNull String getText() {
        return this.text;
    }

    /**
     * Gets the output format.
     *
     * @return The format that the speech will be outputted in.
     */
    public @NotNull String getFormat() {
        return this.format;
    }

    /**
     * Gets the voice.
     *
     * @return The voice used for the speech.
     */
    public @NotNull Voice getVoice() {
        return this.voice;
    }

    /**
     * Gets the prosody settings.
     *
     * @return The prosody settings for the voice.
     */
    public @NotNull ProsodySettings getProsodySettings() {
        return this.prosodySettings;
    }
}
