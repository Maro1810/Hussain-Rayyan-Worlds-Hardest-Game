import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Barrier extends Entity {
    
    private int x, y;
    private double xLength, yLength;

    private double scaleWidth, scaleHeight;

    //Must declare this as transient since Image is not Serializable
    private transient Image barrier;	
	private transient AffineTransform tx;

    private Rectangle hitbox;

    public Barrier(int x, int y, double xLength, double yLength) {
        super(EntityType.BARRIER, x, y);
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;

        scaleWidth = 1.0*xLength; 
        scaleHeight = 1.0*yLength;

        hitbox = new Rectangle(x, y, (int)(scaleWidth*27), (int)(scaleHeight*27));

        barrier = getImage("/imgs/" + "Barrier.png");
        tx = AffineTransform.getTranslateInstance(0, 0);

        init(x, y);
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
		
        this.move();

        hitbox.setBounds(x, y, (int)(scaleWidth*27), (int)(scaleHeight*27));
		
		init(x,y);

		g2.drawImage(barrier, tx, null);

        
		// g.setColor(Color.RED);
		// g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    @Override
    public void collision(Entity e) {
        if (hitbox.intersects(e.getHitbox()) && e.type == EntityType.PLAYER) {
            e.collision(this);
        }
    }
    @Override
	public void reset() {
		this.x = startX;
		this.y = startY;
		init(x,y);
	}

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    protected void init(double a, double b) {
        tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
    }

    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void fetchImage() {
        barrier = getImage("/imgs/" + "Barrier.png");
    }

    @Override
    public void setAffineTransform() {
        tx = AffineTransform.getTranslateInstance(0, 0);
    }

}
