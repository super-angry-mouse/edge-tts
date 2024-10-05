package me.superangrymouse.edgetts;

/**
 * Provides constants for possible output formats.
 */
public final class OutputFormats {
    public static final String AUDIO_24KHZ_48KBITRATE_MONO_MP3 = "audio-24khz-48kbitrate-mono-mp3";
    public static final String RAW_48KHZ_16BIT_MONO_PCM  = "raw-48khz-16bit-mono-pcm";

    private OutputFormats() {
        throw new UnsupportedOperationException("can not instantiate util class");
    }
}
