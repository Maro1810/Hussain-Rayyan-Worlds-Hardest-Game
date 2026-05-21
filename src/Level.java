import java.awt.Graphics;
import java.awt.Graphics2D;
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
            e.paint(g);
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Entity> entities = new ArrayList<>();
        
        Ball ball1 = new Ball(300, 1, 1, 0, 15);
        Ball ball2 = new Ball(20, 1, 10, 0, 20);
        Ball ball3 = new Ball(100, 1, 0, 10, 20);
        Coin c1 = new Coin(400, 400, 0, 0, 20);
        Coin c2 = new Coin(50, 50, 0, 2, 20);
        Barrier b = new Barrier(0,500,200, 5);
        Barrier b2 = new Barrier(500,500, 72, 200);
        SafeZone s = new SafeZone(700,0,50,50, true);
        Player player = new Player();

        entities.add(ball1);
        entities.add(ball2);
        // entities.add(ball3);
        entities.add(c1);
        entities.add(c2);
        entities.add(b);
        entities.add(b2);
        entities.add(s);
        entities.add(player);

        Level level = new Level("Hi");

        level.addEntities(entities);

        level.save();
    }

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

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void addEntities(List<Entity> e) {
        entities.addAll(e);
    }

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

    public static Level load(String path) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(Entity.class, new EntityDeserializer()).create();

        File file = new File(path);
        
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
