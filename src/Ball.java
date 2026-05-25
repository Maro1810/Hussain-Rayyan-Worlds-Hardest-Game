import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.function.Function;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball extends Entity {
    
    private int x, y, vx, vy, xRadius, yRadius, velocity;
    private int radius;

    private double scaleWidth, scaleHeight;

    //Must declare this as transient since Image is not Serializable
    private transient Image ball;	
	private transient AffineTransform tx;

    private Rectangle hitbox;
    BallType ballType;

    private double angle = 0;

    public Ball(int x, int y, int vx, int vy, int radius) {
        super(EntityType.BALL, x, y);
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

        ballType = BallType.FREE;

        init(x, y);
    }

    public Ball(int x, int y, int xRadius, int yRadius, int velocity, int radius) {
        super(EntityType.BALL, x, y);

        this.x = x;
        this.y = y;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.velocity = velocity;

        this.radius = radius;

        ballType = BallType.LOOP;
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
		
        if (Frame.mode == Mode.PLAYING) {
            this.move();
        }

        if (ballType == BallType.LOOP) {
            g.setColor(Color.RED);
            g.drawOval(startX+2*xRadius, startY+2*yRadius, 2*xRadius, 2*yRadius);
        }

        hitbox.setBounds(x, y, (int)(scaleWidth*9), (int)(scaleHeight*9));
		
		init(x,y);

        if (x + (scaleWidth*9) >= 1000 || x <= 0 || y + (scaleHeight*9) >= 710 || y <= 0) {
            vx = -vx;
            vy = -vy;
        }

		g2.drawImage(ball, tx, null);

        if (Frame.mode == Mode.EDITOR) {
            drawArrow(g);
        }

        
		// g.setColor(Color.RED);
		// g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }

    
    public void drawArrow(Graphics g) {
        g.setColor(Color.BLACK);

        if (vx == 0 && vy == 0) {
            return;
        }
        
        int x = this.x + radius;
        int y = this.y + radius;
        g.drawLine(x, y, x + vx*10, y + vy*10);

        //drawing the arrowhead
        if (vx != 0 && vy != 0) {
            int x_1 = (x+vx*10)-5;
            int x_2 = (x+vx*10)+5;

            int x_3 = 0;
            if (vx > 0) {
                x_3 = (x+vx*10)+7;
            }
            else {
                x_3 = (x+vx*10)-7;
            }

            Function<Integer, Integer> linearMap = (x_param) -> {
                int y_param = (int) (((double) vy/vx)*(x_param-(x+vx*10))+(y+vy*10));

                return y_param;
            };

            Function<Integer, Integer> linearMap2 = (x_param) -> {
                int y_param = (int) (((double) -vx/vy)*(x_param-(x+vx*10))+(y+vy*10));

                return y_param;
            };

            int y_1 = linearMap2.apply(x_1);
            int y_2 = linearMap2.apply(x_2);
            int y_3 = linearMap.apply(x_3);

            int[] xPoints = {x_1, x_2, x_3};
            int[] yPoints = {y_1, y_2, y_3};

            g.fillPolygon(xPoints, yPoints, 3);

            // g.drawLine(x_1, y_1, x_2, y_2);
        }

        else if (vx == 0 && vy != 0) {
            int x_2 = x - 6;
            int x_3 = x + 6;

            int y_1 = y+vy*10;
            int y_2 = 0;

            if (vy > 0) {
                y_2 = y+vy*10+15;
            }
            else {
                y_2 = y+vy*10-15;
            }

            g.fillPolygon(new int[] {x, x_2, x_3}, new int[] {y_2, y_1, y_1}, 3);
        }
        else {
            int y_2 = y - 6;
            int y_3 = y + 6;

            int x_1 = x+vx*10;
            int x_2 = 0;

            if (vx > 0) {
                x_2 = x+vx*10+15;
            }
            else {
                x_2 = x+vx*10-15;
            }

            g.fillPolygon(new int[] {x_2, x_1, x_1}, new int[] {y, y_2, y_3}, 3);
        }
        
    }

    @Override
    public void collision(Entity e) {
        if (hitbox.intersects(e.getHitbox()) && e.type != EntityType.PLAYER && e.type != EntityType.COIN) {

            if (Math.signum(vx) != Math.signum(e.getVx()) || Math.signum(vy) != Math.signum(e.getVy())) {
                vx = -vx;
                vy = -vy;
            }

            else {
                if (Math.hypot(vx, vy) < Math.hypot(e.getVx(), e.getVy())) {
                    return;
                }
                else {
                    vx = -vx;
                    vy = -vy;
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
        return "Ball at (" + x + ", " + y + ") with velocity (" + vx + ", " + vy + ")";
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
        if (ballType == BallType.FREE) {
            x += vx;
            y += vy;
        }
        else {

            System.out.println(angle);

            x = (int) (startX+xRadius+xRadius*Math.cos(angle));
            y = (int) (startY+yRadius+yRadius*Math.sin(angle));

            angle += 0.05*velocity;
        }
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
        ball = getImage("/imgs/" + "Ball.png");
    }

    @Override
    public void setAffineTransform() {
        tx = AffineTransform.getTranslateInstance(0, 0);
    }

}

enum BallType {
    FREE,
    LOOP
}
