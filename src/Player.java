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
	private AffineTransform tx;
	
	private int x, y;						//position of the object
	private int vx, vy;						//movement variables
	private double scaleWidth = 4.5;		//change to scale image
	private double scaleHeight = 4.5; 

	private Rectangle hitbox;

	//change to scale image
	public Player() {
		super(false, false, true, false);
		square = getImage("/imgs/"+"Player.png"); //load the image for Tree

		x = 100;
		y = 100;
		vx = 0;
		vy = 0;
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		hitbox = new Rectangle(x, y, (int) (9 * scaleWidth), (int) (9 * scaleHeight));
		
		init(x, y); 				//initialize the location of the image
									//use your variables	
	}
	
	public Player(int x, int y) {
		this();
		this.x = x;
		this.y = y;
		init(x,y);
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		this.move();

		hitbox.setBounds(x, y, (int) (9 * scaleWidth), (int) (9 * scaleHeight));	
		
		init(x,y);
		
		g2.drawImage(square, tx, null);

		
		g.setColor(Color.green);
		g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());

	}

	public void reset(Point location) {
		this.x = location.x;
		this.y = location.y;
		init(x,y);
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

	@Override
	public void collision(Entity e) {
		if (hitbox.intersects(e.getHitbox()) && e.kills) {
			this.reset(new Point(100, 100));
		}

		else if (hitbox.intersects(e.getHitbox()) && e.collectable) {
			e.collect();

		}

		else if (hitbox.intersects(e.getHitbox()) && e.wall) {
			//handle collisions with walls

			//this isnt right since you get stuck to the wall and cant move after touching the wall
			if (y > e.getY() && vy < 0) {
				vy = 0;
			}

			if (y < e.getY() && vy > 0) {
				vy = 0;
			}

		 	if (x > e.getX() && vx < 0) {
				vx = 0;
			}

			if (x < e.getX() && vx > 0) {
				vx = 0;
			}
		}
	}

	@Override
    protected void init(double a, double b) {
        tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
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

	@Override
	public void collect() {
		// TODO Auto-generated method stub
		
	}


}
