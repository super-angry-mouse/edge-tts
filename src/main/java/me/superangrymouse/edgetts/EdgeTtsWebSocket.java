package me.superangrymouse.edgetts;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Web socket that connects to Microsoft's online TTS service.
 */
public final class EdgeTtsWebSocket extends WebSocketClient {
    private final TtsSettings ttsSettings;
    private final OutputStream outputStream;
    private final List<Consumer<Boolean>> onFinishListeners;

    /**
     * Creates a web socket to the TTS service, but does not connect.
     *
     * @param webSocketUri The URI of Microsoft's TTS service
     * @param ttsSettings The settings used for the TTS data once the webhook is connected.
     * @param outputStream The output stream that collects the data provided once connected.
     */
    public EdgeTtsWebSocket(
            @NotNull URI webSocketUri,
            @NotNull TtsSettings ttsSettings,
            @NotNull OutputStream outputStream
    ) {
        super(webSocketUri);
        this.ttsSettings = Checks.notNullArg(ttsSettings, "ttsSettings");
        this.outputStream = Checks.notNullArg(outputStream, "outputStream");
        this.onFinishListeners = new ArrayList<>();
    }

    @Override
    @ApiStatus.Internal
    public void onOpen(ServerHandshake handshakeData) {
        super.send(this.createPrepareWebHookData());
        super.send(this.createTtsRequestData());
    }

    private String createPrepareWebHookData() {
        return """
                Content-Type:application/json; charset=utf-8\r
                Path:speech.config\r
                \r
                {
                    "context": {
                        "synthesis": {
                            "audio": {
                                "metadataoptions": {
                                    "sentenceBoundaryEnabled": false,
                                    "wordBoundaryEnabled": true
                                },
                                "outputFormat": "{output-format}"
                            }
                        }
                    }
                }
                """.replace("{output-format}", this.ttsSettings.getFormat());
    }

    private String createTtsRequestData() {
        return """
                X-RequestId: {id}\r
                Content-Type:application/ssml+xml\r
                Path:ssml\r
                \r
                <speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xml:lang='en-US'>
                    <voice name='{voice-name}'>
                        <prosody pitch='{pitch}' rate='{rate}' volume='{volume}'>
                            {text}
                        </prosody>
                    </voice>
                </speak>
                """
                .replace("{id}", UUID.randomUUID().toString().replace("-", ""))
                .replace("{voice-name}", this.ttsSettings.getVoice().getName())
                .replace("{pitch}", this.ttsSettings.getProsodySettings().getPitchFormatted())
                .replace("{rate}", this.ttsSettings.getProsodySettings().getRateFormatted())
                .replace("{volume}", this.ttsSettings.getProsodySettings().getVolumeFormatted())
                .replace("{text}", this.ttsSettings.getText());
    }

    @Override
    @ApiStatus.Internal
    public void onMessage(ByteBuffer bytes) {
        byte[] byteArray = bytes.array();
        int byteDataStart = this.findDataStartIndex(byteArray);
        if (byteDataStart != -1) {
            try {
                this.outputStream.write(byteArray, byteDataStart, byteArray.length - byteDataStart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final byte[] AUDIO_DATA_START_INDICATOR = "Path:audio\r\n".getBytes();

    private int findDataStartIndex(byte[] bytes) {
        boolean match = false;
        for (int i = 0; i < bytes.length - AUDIO_DATA_START_INDICATOR.length; i++) {
            for (int lookAheadIndex = 0; lookAheadIndex < AUDIO_DATA_START_INDICATOR.length; lookAheadIndex++) {
                if (AUDIO_DATA_START_INDICATOR[lookAheadIndex] != bytes[i + lookAheadIndex]) {
                    match = false;
                    break;
                }

                match = true;
            }

            if (match) {
                return i + AUDIO_DATA_START_INDICATOR.length;
            }
        }

        return -1;
    }

    @Override
    @ApiStatus.Internal
    public void onMessage(String message) {
        if (message.contains("Path:turn.end")) {
            super.close();
        }
    }

    @Override
    @ApiStatus.Internal
    public void onClose(int code, String reason, boolean remote) {
        this.onFinishListeners.forEach(listener -> listener.accept(code == CloseFrame.NORMAL));
    }

    @Override
    @ApiStatus.Internal
    public void onError(Exception e) {
        e.printStackTrace();
    }

    /**
     * Registers a listener which is executed once this webhook finishes (successfully or not).
     *
     * @param listener The listener.
     */
    public void registerFinishListener(@NotNull Consumer<@NotNull Boolean> listener) {
        Checks.notNullArg(listener, "listener");
        this.onFinishListeners.add(listener);
    }
}
