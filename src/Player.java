import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Player extends Entity {

	//Must declare this transient since Image is not Serializable
	private transient Image square;	
	private transient AffineTransform tx;
	
	private int x, y;//position of the object
	private int vx, vy;						//movement variables
	private double scaleWidth = 4.5;		//change to scale image
	private double scaleHeight = 4.5; 
	private boolean dead, winning;
	// private int prevX, prevY;

	private Rectangle hitbox;

	
	public Player(int x, int y) {
		super(EntityType.PLAYER, x, y);
		square = getImage("/imgs/"+"Player.png"); //load the image for Tree
		vx = 0;
		vy = 0;
		dead = false;
		winning = false;
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		hitbox = new Rectangle(x, y, (int) (9 * scaleWidth), (int) (9 * scaleHeight));
	
		this.x = x;
		this.y = y;
		init(x,y);
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		this.moveX();
		this.moveY();

		hitbox.setBounds(x, y, (int) (9 * scaleWidth), (int) (9 * scaleHeight));	
		
		init(x,y);
		
		g2.drawImage(square, tx, null);

		if (x <= 0) {
			x = 0;
		}

		if (y <= 0) {
			y = 0;
		}

		if (x >= 985) {
			x = 985;
		}

		if (y >= 660) {
			y = 660;
		}

		
		// g.setColor(Color.green);
		// g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
		winning = false;
	}
	
	@Override
	public void reset() {
		this.x = startX;
		this.y = startY;
		init(x,y);
		dead = false;
		winning = false;
	}

	@Override
	public Rectangle getHitbox() {
		return hitbox;
	}

	@Override
	public void move() {
		x += vx;
		y += vy;
	}
	public void moveX() {
		x += vx;
	}
	public void moveY() {
		y += vy;
	}

	@Override
	public void collision(Entity e) {
		if (hitbox.intersects(e.getHitbox()) && e.type == EntityType.BALL) {
			dead = true;
		}

		if (hitbox.intersects(e.getHitbox()) && e.type == EntityType.COIN) {
			e.collect();

		}
		
		if (hitbox.intersects(e.getHitbox()) && e.type == EntityType.BARRIER) {
			Rectangle b = e.getHitbox();

			double overlapX = 0;

			if(hitbox.x < b.x) {
    			overlapX = hitbox.x + hitbox.width - b.x; 
			} else {
    			overlapX = b.x + b.width - hitbox.x; 
			}

			double overlapY = 0;
			
			if(hitbox.y < b.y) {
    			overlapY = hitbox.y + hitbox.height - b.y; 
			} else {
    			overlapY = b.y + b.height - hitbox.y; 
			}

			if(overlapX < overlapY) {
    			if(hitbox.x < b.x) {
        			x -= overlapX; 
    			} else {
        			x += overlapX; 
    			}
    			vx = 0;
			} else {
    			if(hitbox.y < b.y) {
        			y -= overlapY; 
    			} else {
        			y += overlapY; 
    			}
    			vy = 0;
			}

		hitbox.setLocation(x, y);
	}

	if (hitbox.intersects(e.getHitbox()) && e.type == EntityType.SAFE_ZONE) {
		this.startX = e.getX()+4;
		this.startY = e.getY()+4;
	}
//		if(hitbox.intersects(e.getHitbox()) && !e.wall && !e.kills && !e.collectable) {
//			SafeZone s = (SafeZone) e;
//			if(s.isEnd()) {
//				winning = true;
//				System.out.println("win");
//			}
//		}
		
	}

	@Override
    protected void init(double a, double b) {
        tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
    }
	public boolean isDead() {
		return dead;
	}

	//TODO remove this since it was only used for testing
	public String toString() {
		return "this is the player at " + x + ", " + y;
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
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
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
		square = getImage("/imgs/" + "Player.png");
	}

	public boolean winning() {
		return winning;
	}

	@Override
    public void setAffineTransform() {
        tx = AffineTransform.getTranslateInstance(0, 0);
    }


}
