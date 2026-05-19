import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        entities.add(new Ball(0, 0, 10, 10 ,10));
        
        Player player = new Player();
        Coin c1 = new Coin(400, 400, 0, 0, 20);
        Coin c2 = new Coin(50, 50, 0, 2, 20);
        Barrier b = new Barrier(0,500,0,0,200, 5);
        Barrier b2 = new Barrier(500,500,0,0, 72, 200);

        entities.add(c1);
        entities.add(c2);
        entities.add(b);
        entities.add(b2);
        entities.add(player);

        Level level = new Level("Hi");

        level.getEntities().addAll(entities);

        level.save();

        // Level.load("src/levels/Hi.json");
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
