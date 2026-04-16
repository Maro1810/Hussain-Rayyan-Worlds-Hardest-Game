import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class BackGround{
	private Image forward, backward, left, right; 	
	private AffineTransform tx;
					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object				//movement variables
	double scaleWidth = 10;		//change to scale image
	double scaleHeight = 10; 		//change to scale image

	public BackGround() {
		forward 	= getImage("/imgs/"+"BG.png"); //load the image for Tree
		//alter these
		width = 1000;
		height = 1000;
		x = 0;
		y = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		init(x,y);
		
		

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = BackGround.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}