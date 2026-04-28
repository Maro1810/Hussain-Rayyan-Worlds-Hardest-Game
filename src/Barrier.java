import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Barrier extends Entity {
    
    private int x, y, vx, vy;
    private int length;

    private double scaleWidth, scaleHeight;

    //Must declare this as transient since Image is not Serializable
    private transient Image barrier;	
	private AffineTransform tx;

    private Rectangle hitbox;

    public Barrier(int x, int y, int vx, int vy, int length) {
        super(false, false, false, true);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.length = length;

        //since original image is 9x9, we scale to fit the radius
        scaleWidth = (2 * length) / 36; 
        scaleHeight = (10) / 9;

        hitbox = new Rectangle(x, y, (int)(scaleWidth*36), (int)(scaleHeight*9));

        barrier = getImage("/imgs/" + "Border.png");
        tx = AffineTransform.getTranslateInstance(0, 0);

        init(x, y);
    }
    
    @Override
    public void collect() {
    	
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
		
        this.move();

        hitbox.setBounds(x, y, (int)(scaleWidth*36), (int)(scaleHeight*9));
		
		init(x,y);

        if (x + (scaleWidth*7) >= 1000 || x <= 0 || y + (scaleHeight*9) >= 980 || y <= 0) {
            vx = -vx;
            vy = -vy;
        }

		g2.drawImage(barrier, tx, null);

        
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    @Override
    public void collision(Entity e) {
        if (hitbox.intersects(e.getHitbox()) && e.player) {
            e.collision(this);
        }
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    //TODO remove this since it was only used for testing
    public String toString() {
        return "Barrier at (" + x + ", " + y + ") with velocity (" + vx + ", " + vy + ")";
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
    public void move() {
        x += vx;
        y += vy;
    }

    @Override
    public void setVx(int vx) {
        this.vx = vx;
    }

    @Override
    public void setVy(int vy) {
        this.vy = vy;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getVx() {
        return vx;
    }

    @Override
    public int getVy() {
        return vy;
    }

    @Override
    public void fetchImage() {
        barrier = getImage("/imgs/" + "Border.png");
    }

}
