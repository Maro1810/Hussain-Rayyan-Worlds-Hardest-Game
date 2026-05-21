
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball extends Entity {
    
    private int x, y, vx, vy;
    private int radius;
    private Path path;
    private int curr;
    private int dir;

    private double scaleWidth, scaleHeight;

    //Must declare this as transient since Image is not Serializable
    private transient Image ball;	
	private transient AffineTransform tx;

    private Rectangle hitbox;

    public Ball(int x, int y, int vx, int vy, int radius, String type) {
        super(EntityType.BALL, x, y);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        path = new Path(type);
        path.addPoints(x,y);
        curr = 0;
        dir = 1;
        

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
            vx = -vx;
            vy = -vy;
        }

		g2.drawImage(ball, tx, null);

        
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
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
    		if(path.getType().equals("Free")) {
    			x+= vx;
    			y+=vy;
    		}
    	   
       if(path.getType().equals("Linear")) {
    	   		if(dir == 1 && path.getPath().size() > 1) {
    	   			while(x != path.getPoint(curr +1).getX()) {
    	   				if( x <  path.getPoint(curr +1).getX()) {
    	   					x += vx;
    	   				}else {
    	   					x -= vx;
    	   				}
    	   			}
    	   			while(y != path.getPoint(curr +1).getY()) {
    	   				if( y <  path.getPoint(curr +1).getY()) {
    	   					y += vy;
    	   				}else {
    	   					y -= vy;
    	   				}
    	   			}
    	   			if(x == path.getPoint(curr +1).getX() && y == path.getPoint(curr +1).getY() ) {
    	   				curr++;
    	   			}
    	   			if(curr > path.getPath().size()) {
    	   				dir = -1;
    	   			}
    	   		}
    	   		if(dir == -1 && path.getPath().size() > 1) {
    	   			while(x != path.getPoint(curr -1).getX()) {
    	   				if( x <  path.getPoint(curr -1).getX()) {
    	   					x += vx;
    	   				}else {
    	   					x -= vx;
    	   				}
    	   			}
    	   			while(y != path.getPoint(curr -1).getY()) {
    	   				if( y <  path.getPoint(curr -1).getY()) {
    	   					y += vy;
    	   				}else {
    	   					y -= vy;
    	   				}
    	   			}
    	   			if(x == path.getPoint(curr - 1).getX() && y == path.getPoint(curr - 1).getY()) {
    	   				curr--;
    	   			}
    	   			if(curr < 0) {
    	   				dir = 1;
    	   			}
    	   		}
       }
    }
    
    public Path getPath() {
    		return path;
    }
    public void setType(String s) {
    		path.setType(s);
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

