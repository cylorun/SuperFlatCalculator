package com.cylorun;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SuperFlatCalculatorOptions {

    public Integer[] win_loc = new Integer[]{0, 0};
    private static SuperFlatCalculatorOptions instance;
    private static Path CONFIG_PATH = Path.of("config.json");
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static SuperFlatCalculatorOptions getInstance() {
        if (instance == null) {
            if (Files.exists(CONFIG_PATH)) {
                try {
                    instance = GSON.fromJson(new String((Files.readAllBytes(CONFIG_PATH))), SuperFlatCalculatorOptions.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                instance = new SuperFlatCalculatorOptions();
            }
        }
        return instance;
    }

    public static void save() {
        FileWriter writer;
        try {
            writer = new FileWriter(CONFIG_PATH.toFile());
            GSON.toJson(instance, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
