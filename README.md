# edge-tts
Java library that simplifies Microsoft Edge's online text-to-speech service.

## Installation
Coming soon...

## Usage
```java
public final class TtsExample {
    public static void main(String[] args) {
        Path path = Path.of(System.getProperty("user.dir") + "/test.mp3"); // The output path

        String text = "Hello! How was your day?"; // The text that is translated to speech
        String outputFormat = OutputFormats.AUDIO_24KHZ_48KBITRATE_MONO_MP3; // The output format
        Voice voice = Voices.EN_US_GUY_NEURAL; // The voice used for the speech
        // Prosody settings (+10% rate, +100% volume, -10% pitch) 
        ProsodySettings prosodySettings = new ProsodySettings(10, 100, -10);
        // Compose everything into TTS settings
        TtsSettings ttsSettings = new TtsSettings(text, outputFormat, voice, prosodySettings);

        EdgeTtsClient client = new EdgeTtsClient(); // Instantiate the TTS client.
        client.saveToFile(path, ttsSettings).join(); // Save the TTS data to the output path.
    }
}
```
