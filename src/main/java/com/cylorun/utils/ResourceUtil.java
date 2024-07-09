package com.cylorun.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResourceUtil {
    public static JsonObject loadJsonResource(URL url) {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = url.openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
    }

    public static BufferedImage loadImageResource(String path) throws IOException {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + path);
            }
            return ImageIO.read(inputStream);
        }
    }
}
