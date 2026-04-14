import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Player{
	private Image square;	
	private AffineTransform tx;
	
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 4.5;		//change to scale image
	double scaleHeight = 4.5; 
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
	
	public void setVx(int v) {
		vx = v;
	}
	
	public void setVy(int w) {
		vy = w;
	}
	
	public void setX(int posx) {
		x = posx;
	}
	
	public void setY(int posy) {
		y = posy;
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