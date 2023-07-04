package net.pixeldream.mythicmobs.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.pixeldream.mythicmobs.entity.MushroomVariant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MushroomLines {
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> greetings;

    public MushroomLines(MushroomVariant variant) {
        if (variant.equals(MushroomVariant.RED)) {
            setUpRed();
        }
        else {
            setUpBrown();
        }
    }

    private void setUpBrown() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/mythicmobs/other/brown_mushroom_lines.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            fillLines(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpRed() {
        greetings = new ArrayList<>();
        greetings.add("Hello there, ");
        greetings.add("Hey there, ");
        greetings.add("Howdy, ");
        greetings.add("Howdy-do, ");
        greetings.add("Salutations, ");
        greetings.add("Hiya, ");
        greetings.add("Godspeed, ");
        try {
            InputStream inputStream = getClass().getResourceAsStream("/assets/mythicmobs/other/red_mushroom_lines.txt");
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
        String line;
        do {
            line = lines.get(randomLine.nextInt(lines.size()));
        } while (line.equals("playerGreeting"));
        return Text.literal(line);
    }

    public Text getLine(PlayerEntity player) {
        Random randomLine = new Random();
        String line = lines.get(randomLine.nextInt(lines.size()));
        if (line.equals("playerGreeting")) {
            Random randomGreeting = new Random();
            line = greetings.get(randomGreeting.nextInt(greetings.size())) + player.getEntityName() + '!';
        }
        return Text.literal(line);
    }
}
