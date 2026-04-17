import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public abstract class Obstacle {

    protected boolean kills;

    public Obstacle(boolean kills) {
        this.kills = kills;
    }

    public abstract void paint(Graphics g);

    public void move() {}

    public abstract void collision(Player p);

    public abstract void collision(Obstacle o);

    protected abstract void init(double a, double b);

    public abstract int getX();

    public abstract int getY();

    public void setVx(int vx) {}

    public void setVy(int vy) {}

    public void setX(int x) {}

    public void setY(int y) {}

    protected Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Obstacle.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
}
