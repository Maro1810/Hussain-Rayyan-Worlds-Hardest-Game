
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class BallRots extends Entity {
    
    private int x, y;
    private int radius;
    private double v;
    private Point center;
    private int rotRadius;
    private double angle;

    private double scaleWidth, scaleHeight;

    //Must declare this as transient since Image is not Serializable
    private transient Image ball;	
	private transient AffineTransform tx;

    private Rectangle hitbox;

    public BallRots(int x, int y, double v, int radius, int rotRadius) {
        super(EntityType.BALL, x, y);
        this.x = x+rotRadius;
        this.y = y;
        this.v = v;
        this.radius = radius;
        this.center = new Point(x,y);
        this.rotRadius = rotRadius;
        this.angle = 0;
        

        //since original image is 9x9, we scale to fit the radius
        scaleWidth = (2 * radius) / 9.0; 
        scaleHeight = (2 * radius) / 9.0;

        hitbox = new Rectangle(x, y, (int)(scaleWidth*9), (int)(scaleHeight*9));

        ball = getImage("/imgs/" + "Ball.png");
        tx = AffineTransform.getTranslateInstance(0, 0);

        init(x, y);
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
		
        this.move();

        hitbox.setBounds(x, y, (int)(scaleWidth*9), (int)(scaleHeight*9));
		
		init(x,y);

        if (x + (scaleWidth*9) >= 1000 || x <= 0 || y + (scaleHeight*9) >= 710 || y <= 0) {
            v = -v;
            v = -v;
        }

		g2.drawImage(ball, tx, null);

        
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    @Override
    public void collision(Entity e) {
        if (hitbox.intersects(e.getHitbox()) && e.type != EntityType.PLAYER && e.type != EntityType.COIN) {

            if (Math.signum(v) != Math.signum(e.getVx()) || Math.signum(v) != Math.signum(e.getVy())) {
                v = -v;
            }

            else {
                if (Math.hypot(v, v) < Math.hypot(e.getVx(), e.getVy())) {
                    return;
                }
                else {
                    v = -v;
                }
            }
        }

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

    //TODO remove this since it was only used for testing
    public String toString() {
        return "Ball at (" + x + ", " + y + ") with velocity (v)";
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
    	 angle += v;
    	 x = (int)(center.x + rotRadius * Math.cos(Math.toRadians(angle)));
    	 y = (int)(center.y + rotRadius * Math.sin(Math.toRadians(angle)));
    	    
    	 hitbox.setLocation(x, y);
    }
    

    @Override
    public void setVx(int vx) {
        this.v = vx;
    }

    @Override
    public void setVy(int vy) {
        this.y = vy;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getVx() {
        return (int)v;
    }

    @Override
    public int getVy() {
        return (int)v;
    }

    @Override
    public void fetchImage() {
        ball = getImage("/imgs/" + "Ball.png");
    }

    @Override
    public void setAffineTransform() {
        tx = AffineTransform.getTranslateInstance(0, 0);
    }

}

