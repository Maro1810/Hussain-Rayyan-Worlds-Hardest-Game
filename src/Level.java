import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Level {
    
    private List<Entity> entities;
    private String name;

    public Level(String name) {
        this.name = name;

        entities = new ArrayList<>();
    }

    public void paint(Graphics g) {
        for (Entity e : entities) {
            if (e.type != EntityType.SAFE_ZONE) {
                continue;
            }
            e.paint(g);
        }
        for (Entity e : entities) {
            if (e.type == EntityType.SAFE_ZONE) {
                continue;
            }
            e.paint(g);
        }
    }

    //Save a level using gson and writing to a file using FileWriter
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try {
            FileWriter writer = new FileWriter("src/levels/" + this.name + ".json");

            String json = gson.toJson(this);

            writer.write(json);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public boolean containsEntity(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public int numCoins() {
        int count = 0;

        for (Entity e : entities) {
            if (e instanceof Coin) {
                count++;
            }
        }
        
        return count;
    }

    //Generates a name by generating "myLevel_" and then appending the next free integer to the end
    public static String generateName() {
        String name = "myLevel_";

        int num = 1;

        File file = new File("src/levels");

        String[] names = file.list();

        for (int i = 0; i < names.length; i++) {
            int index = names[i].indexOf(".json");

            names[i] = names[i].substring(0, index);
        }

        while (Arrays.asList(names).contains(name+num)) {
            num++;
        }

        return name+num;
    }

    //Load a level using gson and filereader
    public static Level load(String path) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Entity.class, new EntityDeserializer()).create();

        File file = new File("src/levels/" + path);
        
        FileReader reader = new FileReader(file);

        Level loadedLevel = gson.fromJson(reader, Level.class);

        return loadedLevel;
    }
}

//https://medium.com/@alexandre.therrien3/personalized-serializer-and-deserializer-using-java-gson-library-c079de3974d4
class EntityDeserializer implements JsonDeserializer<Entity> {

    @Override
    public Entity deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String entityType = jsonObject.get("type").getAsString();

        switch (entityType) {
            case "BALL":
                return context.deserialize(jsonObject, Ball.class);
            case "BARRIER":
                return context.deserialize(jsonObject, Barrier.class);
            case "PLAYER":
                return context.deserialize(jsonObject, Player.class);
            case "COIN":
                return context.deserialize(jsonObject, Coin.class);
            case "SAFE_ZONE":
                return context.deserialize(jsonObject, SafeZone.class);
            case "BUTTON":
                return context.deserialize(jsonObject, Button.class);
            default:
                throw new JsonParseException("Unknown entity!");
        }
    }
    
}
