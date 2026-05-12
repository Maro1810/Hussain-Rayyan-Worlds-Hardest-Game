// public class Button {
//     private Image bg; 	
// 	private AffineTransform tx;

// 	int width, height;
// 	int x, y;						//position of the object				//movement variables
// 	double scaleWidth = 0;		//change to scale image
// 	double scaleHeight = 0; 		//change to scale image

// 	public BackGround() throws InvalidBackgroundException {
// 		//alter these
// 		width = 1000;
// 		height = 1000;
// 		x = 0;
// 		y = 0;
		
// 		tx = AffineTransform.getTranslateInstance(0, 0);

// 		setBackground(0);
		
// 		init(x, y); 	//initialize the location of the image
// 									//use your variables
		
// 	}
	

// 	public void paint(Graphics g) {
// 		//these are the 2 lines of code needed draw an image on the screen
// 		Graphics2D g2 = (Graphics2D) g;
// 		init(x,y);
		
// 		g2.drawImage(bg, tx, null);

// 	}
	
// 	private void init(double a, double b) {
// 		tx.setToTranslation(a, b);
// 		tx.scale(scaleWidth, scaleHeight);
// 	}

// 	private Image getImage(String path) {
// 		Image tempImage = null;
// 		try {
// 			URL imageURL = BackGround.class.getResource(path);
// 			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
// 		return tempImage;
// 	}
// }
