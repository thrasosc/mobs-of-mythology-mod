package net.pixeldream.mythicmobs.util;

import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MushroomLines {
    private static ArrayList<String> lines = new ArrayList<>();

    public MushroomLines() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/mythicmobs/other/mushroom_lines.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            fillLines(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillLines(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    }

    public Text getLine() {
        Random randomLine = new Random();
        return Text.literal(lines.get(randomLine.nextInt(lines.size())));
    }
}
