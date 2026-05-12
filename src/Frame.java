import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener{
	
	Player p = new Player();
	BackGround bg;

	ArrayList<Entity> entities = new ArrayList<>();

	ArrayList<Entity> deserializedEntities = new ArrayList<>();

	SpatialHasher hasher = new SpatialHasher(deserializedEntities, 1000);
	
	int coins = 0;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, InvalidBackgroundException {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
	}
	
	public Frame() throws FileNotFoundException, IOException, ClassNotFoundException, InvalidBackgroundException {
		JFrame menu = new JFrame("Main Menu");
		menu.setSize(new Dimension(1000, 750));
		menu.setBackground(Color.white);
		menu.add(this);
		menu.addMouseListener(this);
		menu.addKeyListener(this);
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer t = new Timer(16, this);
		t.start();
		
		bg = new BackGround();

		menu.setVisible(true);


        Ball ball1 = new Ball(300, 1, 1, 0, 15);
        Ball ball2 = new Ball(20, 1, 10, 0, 20);
        Ball ball3 = new Ball(100, 1, 0, 10, 20);
        Coin c1 = new Coin(400, 400, 0, 0, 20);
        Coin c2 = new Coin(50, 50, 0, 2, 20);
        Barrier b = new Barrier(0,500,0,0,200, 5);
        Barrier b2 = new Barrier(500,500,0,0, 72, 200);
        SafeZone s = new SafeZone(700,0,0,0,50,50, true);
        Player player = new Player();

        entities.add(ball1);
        entities.add(ball2);
        // entities.add(ball3);
        entities.add(c1);
        entities.add(c2);
        entities.add(b);
        entities.add(b2);
        entities.add(s);
        entities.add(player);
		

        FileOutputStream fileOut = new FileOutputStream(new File("src/example.txt"));
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(entities);
        out.close();

        FileInputStream fileIn = new FileInputStream(new File("src/example.txt"));
        ObjectInputStream in = new ObjectInputStream(fileIn);

		deserializedEntities = (ArrayList<Entity>) in.readObject();

        in.close();

		for (int i = 0; i < deserializedEntities.size(); i++) {
			if (deserializedEntities.get(i) instanceof Player) {
				p = (Player) deserializedEntities.get(i);
				deserializedEntities.set(i, p);
			}
			deserializedEntities.get(i).fetchImage();
		}

		hasher.setEntities(deserializedEntities);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);

		bg.paint(g);

		if (bg.getScreen() == 1) {
			hasher.update();

			for (Entity e : deserializedEntities) {
				e.paint(g);
			}
		}
		coins = 0;
		updateCoins();
		win();
		reset();
			
	}
	public void reset() {
		Player p = null;
		for (Entity e : deserializedEntities) {
			if(e instanceof Player ) {
				p = (Player) e;
				if(p.isDead()) {
					for (Entity g : deserializedEntities) {
						g.reset();
					}
				}
			}
			// hasher.update();
			hasher.setEntities(deserializedEntities);
		}
	}
	public void win() {
		Player p = null;
		for (Entity e : deserializedEntities) {
			if(e instanceof Player ) {
				p = (Player) e;
				if(p.winning() && coins <= 0) {
					for (Entity g : deserializedEntities) {
						g.reset();
					}
					System.out.println("You win :)");
				}
			}
			// hasher.update();
			hasher.setEntities(deserializedEntities);
		}
	}
	public void updateCoins() {
		for (Entity e : deserializedEntities) {
			if(e instanceof Coin ) {
				Coin c = (Coin) e;
				if(!c.isCollected()) {
					coins++;
				}
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87) {
			p.setVy(-3);
		}
		if(e.getKeyCode() == 83) {
			p.setVy(3);
		}
		if(e.getKeyCode() == 65) {
			p.setVx(-3);
		}
		if(e.getKeyCode() == 68) {
			p.setVx(3);
		}

		if (e.getKeyCode() == 49) {
			try {
				bg.setBackground(1);
			} catch (InvalidBackgroundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getKeyCode() == 48) {
			try {
				bg.setBackground(0);
			} catch (InvalidBackgroundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getKeyCode() == 80) {
			for (Entity en : deserializedEntities) {
				en.setVx(0);
				en.setVy(0);
			}
		}

		if (e.getKeyCode() == 82) {
			for (Entity en : deserializedEntities) {
				en.reset();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 83) {
			p.setVy(0);
		}
	
		if (e.getKeyCode() == 68 || e.getKeyCode() == 65) {
			p.setVx(0);
		}

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON1) {
			deserializedEntities.add(new Ball(e.getX()-20, e.getY()-20, 5, 5, 15));
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			deserializedEntities.remove(deserializedEntities.size()-1);
		}
		
	}

	// public void remove()

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	

}