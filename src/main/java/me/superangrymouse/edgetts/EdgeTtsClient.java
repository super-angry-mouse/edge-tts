package me.superangrymouse.edgetts;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * A class which simplifies the online TTS process.
 */
public final class EdgeTtsClient {
    private static final String TRUSTED_CLIENT_TOKEN = "6A5AA1D4EAFF4E9FB37E23D68491D6F4";
    private static final URI SYNTHESIZE_URI = URI.create(
            "wss://speech.platform.bing.com/consumer/speech/synthesize/readaloud/edge/v1?TrustedClientToken=" +
                    TRUSTED_CLIENT_TOKEN
    );

    /**
     * Creates the TTS client.
     */
    public EdgeTtsClient() {
    }

    /**
     * Saves the TTS data into a specified path. Creates all parent directories first
     * and also replaces the file at the provided path if it already exists.
     *
     * @param path The path to save the TTS data to.
     * @param settings The settings to use when generating the TTS data.
     * @return The path provided.
     */
    public @NotNull CompletableFuture<@NotNull Path> saveToFile(@NotNull Path path, @NotNull TtsSettings settings) {
        Checks.notNullArg(path, "path");
        Checks.notNullArg(settings, "settings");

        return this.getBytes(settings)
                .thenApply(bytes -> {
                    try {
                        Files.createDirectories(path.getParent());
                        Files.write(path, bytes);
                        return path;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Gets the bytes for the TTS data.
     *
     * @param settings The settings to use when generating the TTS data.
     * @return The bytes of the TTS data in a byte array.
     */
    public CompletableFuture<byte@NotNull[]> getBytes(@NotNull TtsSettings settings) {
        Checks.notNullArg(settings, "settings");

        CompletableFuture<byte[]> future = new CompletableFuture<>();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EdgeTtsWebSocket edgeTtsWebSocket = new EdgeTtsWebSocket(SYNTHESIZE_URI, settings, outputStream);
        edgeTtsWebSocket.registerFinishListener(success -> {
            if (success) {
                future.complete(outputStream.toByteArray());
            } else {
                future.completeExceptionally(new RuntimeException("failed to load byte data"));
            }
        });
        edgeTtsWebSocket.connect();

        return future;
    }
}
