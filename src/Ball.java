import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;

public class Ball extends Obstacle {
    
    private int x, y, vx, vy;
    private int radius;

    private double scaleWidth, scaleHeight;

    private Image ball;	
	private AffineTransform tx;

    public Ball(int x, int y, int vx, int vy, int radius) {
        super(true);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;

        //since original image is 32x32, we scale to fit the radius
        scaleWidth = (2 * radius) / 32.0; 
        scaleHeight = (2 * radius) / 32.0;

        ball = getImage("/imgs/" + "Ball.png");
        tx = AffineTransform.getTranslateInstance(0, 0);

        init(x, y);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
		
        this.move();
		
		init(x,y);
		
		g2.drawImage(ball, tx, null);
    }

    @Override
    public void collision(Player p) {

    }

    @Override
    public void collision(Obstacle o) {

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
