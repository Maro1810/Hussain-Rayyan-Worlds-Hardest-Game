import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Player {
	private Image square;	
	private AffineTransform tx;
	
	private int x, y;						//position of the object
	private int vx, vy;						//movement variables
	private double scaleWidth = 4.5;		//change to scale image
	private double scaleHeight = 4.5; 

	//change to scale image
	public Player() {
		square 	= getImage("/imgs/"+"Player.png"); //load the image for Tree

		x = 100;
		y = 100;
		vx = 0;
		vy = 0;
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		
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
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(square, tx, null);

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	public void setVx(int vx) {
		this.vx = vx;
	}
	
	public void setVy(int vy) {
		this.vy = vy;
	}
	
	public void setX(int posx) {
		this.x = posx;
	}
	
	public void setY(int posy) {
		this.y = posy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Player.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}


}
