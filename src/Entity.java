import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.Serializable;
import java.net.URL;

public abstract class Entity implements Serializable {

    public static final long serialVersionUID = 1L;

    protected EntityType type;
    protected int startX;
    protected int startY;

    public Entity(EntityType type, int x, int y) {
        this.type = type;
        this.startX = x;
        this.startY = y;
    }

    public abstract void paint(Graphics g);

    public void move() {}

    public abstract void collision(Entity e);
    
    public abstract void reset();

    protected abstract void init(double a, double b);

    public abstract int getX();

    public abstract int getY();

    public void setVx(int vx) {}

    public void setVy(int vy) {}

    public int getVx() {
        return 0;
    }

    public int getVy() {
        return 0;
    }

    public void setPosition(int x, int y) {}

    public Rectangle getHitbox() {
        return null;
    }

    protected Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Entity.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    public abstract void fetchImage();

	public abstract void collect();
}

enum EntityType {
    PLAYER, 
    BALL, 
    COIN, 
    SAFE_ZONE,
    BARRIER, 
    BUTTON
}
