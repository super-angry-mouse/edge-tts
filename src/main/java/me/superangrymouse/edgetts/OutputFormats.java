package me.superangrymouse.edgetts;

/**
 * Provides constants for possible output formats.
 */
public final class OutputFormats {
    public static final String AUDIO_24KHZ_48KBITRATE_MONO_MP3 = "audio-24khz-48kbitrate-mono-mp3";
    public static final String AUDIO_24KHZ_96KBITRATE_MONO_MP3 = "audio-24khz-96kbitrate-mono-mp3";
    public static final String WEBM_24KHZ_16BIT_MONO_OPUS = "webm-24khz-16bit-mono-opus";

    private OutputFormats() {
        throw new UnsupportedOperationException("can not instantiate util class");
    }
}
