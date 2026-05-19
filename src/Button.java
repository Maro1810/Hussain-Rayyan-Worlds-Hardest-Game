import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Button extends Entity{
    private transient Image button; 	
	private transient AffineTransform tx;

	int width, height;
	int x, y;						//position of the object				//movement variables
	double scaleWidth = 5;		//change to scale image
	double scaleHeight = 5; 		//change to scale image

	public Button(String path, int x, int y) {
		super(EntityType.BUTTON, x, y);
		width = 1000;
		height = 1000;

		this.x = x;
        this.y = y;

        button = getImage(path);
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 	//initialize the location of the image
									//use your variables
		
	}
	

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		init(x,y);
		
		g2.drawImage(button, tx, null);

	}
	
    @Override
	protected void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}


    @Override
    public void collision(Entity e) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void reset() {
        // TODO Auto-generated method stub
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
    public void fetchImage() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setAffineTransform() {
        tx = AffineTransform.getTranslateInstance(0, 0);
    }
}
