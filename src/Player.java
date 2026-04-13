import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Player{
	private Image forward, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 4.5;		//change to scale image
	double scaleHeight = 4.5; 
	//change to scale image
	public Player() {
		forward 	= getImage("/imgs/"+"Player.png"); //load the image for Tree
		//alter these
		width = 100;
		height = 100;
		x = 500;
		y = 500;
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
	public void setvx(int v) {
		vx = v;
		}
	public void setvy(int w) {
		vy = w;
		}
	public void setx(int posx) {
		x = posx;
	}
	public void sety(int posy) {
		y = posy;
	}
	
	


	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		switch(dir) {
		case 0:
			g2.drawImage(forward, tx, null);
			break;
		case 1:
			g2.drawImage(backward, tx, null);

			break;
		case 2:
			g2.drawImage(left, tx, null);

			break;
		case 3:
			g2.drawImage(right, tx, null);
			break;
		}

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