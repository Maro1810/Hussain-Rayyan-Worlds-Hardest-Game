import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball extends Obstacle {
    
    private int x, y, vx, vy;
    private int radius;

    private double scaleWidth, scaleHeight;

    private Image ball;	
	private AffineTransform tx;

    private Rectangle hitbox;

    public Ball(int x, int y, int vx, int vy, int radius) {
        super(true);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;

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

		g2.drawImage(ball, tx, null);
    }

    @Override
    public void collision(Player p) {
        if (hitbox.intersects(p.getHitbox())) {
            p.reset(new Point(100, 100));
        }
    }

    @Override
    public void collision(Obstacle o) {
        if (hitbox.intersects(o.getHitbox())) {
            vx = -vx;
            vy = -vy;
        }
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
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
    
}
