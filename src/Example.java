import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;

public class Example {
    public static void main(String[] args) throws IOException, ClassNotFoundException{

        ArrayList<Entity> entities = new ArrayList<>();

        Ball ball1 = new Ball(1, 1, 1, 1, 1);
        Ball ball2 = new Ball(20, 20, -1, -1, 1);
        Player player = new Player();

        entities.add(ball1);
        entities.add(ball2);
        entities.add(player);

        FileOutputStream fileOut = new FileOutputStream(new File("C:\\Users\\rayya\\Downloads\\Hussain-Rayyan-Worlds-Hardest-Game\\src\\example.txt"));
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(entities);
        out.close();

        FileInputStream fileIn = new FileInputStream(new File("C:\\Users\\rayya\\Downloads\\Hussain-Rayyan-Worlds-Hardest-Game\\src\\example.txt"));
        ObjectInputStream in = new ObjectInputStream(fileIn);

        ArrayList<Entity> deserializedEntities = (ArrayList<Entity>) in.readObject();

        in.close();

        for (Entity e : deserializedEntities) {
            System.out.println(e);
        }
    }
}