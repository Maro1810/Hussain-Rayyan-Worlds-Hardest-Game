import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class BackGround{
	private Image bg; 	
	private AffineTransform tx;

	int width, height;
	int x, y;						//position of the object				//movement variables
	double scaleWidth = 0;		//change to scale image
	double scaleHeight = 0; 		//change to scale image

	int screen;

	public BackGround(int screen) throws InvalidBackgroundException {
		//alter these
		width = 1000;
		height = 1000;
		x = 0;
		y = 0;
		
		this.screen = screen;
		
		tx = AffineTransform.getTranslateInstance(0, 0);

		setBackground(this.screen);
		
		init(x, y); 	//initialize the location of the image
									//use your variables
		
	}
	

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		init(x,y);
		
		g2.drawImage(bg, tx, null);

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	public void setBackground(int screen) throws InvalidBackgroundException {
		this.screen = screen;

		if (screen == 0) {
			bg = getImage("/imgs/" + "MainMenu.png");

			scaleWidth = Constants.ScaleConstants.MAIN_MENU_SCALE[0];
			scaleHeight = Constants.ScaleConstants.MAIN_MENU_SCALE[1];
		}
		else if (screen == 1) {
			bg 	= getImage("/imgs/"+"BG.png");

			scaleWidth = Constants.ScaleConstants.BG_SCALE[0];
			scaleHeight = Constants.ScaleConstants.BG_SCALE[1];
		} 
		else {
			throw new InvalidBackgroundException("Screen number is invalid!");
		}
	}

	public int getScreen() {
		return screen;
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


class InvalidBackgroundException extends Exception {
	public InvalidBackgroundException(String message) {
		super(message);
	}
}
