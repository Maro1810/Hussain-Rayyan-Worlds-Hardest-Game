import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener{
	
	Player p = new Player();
	BackGround bg;

	ArrayList<Entity> entities = new ArrayList<>();

	SpatialHasher hasher = new SpatialHasher(entities, 1000);

	Level level = Level.load("src/levels/Hi.json");

	Button editor = new Button("/imgs/LvlBuilderButton.png", 400, 450);

	Rectangle editor_hitbox = new Rectangle(400, 450, 90, 90);

	// JButton play = new JButton(new ImageIcon(getClass().getResource("/imgs/PlayButton.png")));
	// JButton play = new JButton("Play");
	
	int coins = 0;
	int objType = 0;

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
		
		bg = new BackGround(0);
		
		// menu.add(play);
		// play.setBounds(400, 450, 50, 50);

		menu.setVisible(true);

		load();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);

		bg.paint(g);

		editor.paint(g);

		g.drawRect(400, 450, 90, 90);

		if (bg.getScreen() == 1) {
			hasher.update();

			level.paint(g);
		}
		coins = 0;
		updateCoins();
		win();
		reset();
			
	}
	public void reset() {
		Player p = null;
		for (Entity e : entities) {
			if(e instanceof Player ) {
				p = (Player) e;
				if(p.isDead()) {
					for (Entity g : entities) {
						g.reset();
					}
				}
			}
			// hasher.update();
			// hasher.setEntities(entities);
		}
	}
	public void win() {
		Player p = null;
		for (Entity e : entities) {
			if(e instanceof Player ) {
				p = (Player) e;
				if(p.winning() && coins <= 0) {
					for (Entity g : entities) {
						g.reset();
					}
					System.out.println("You win :)");
				}
			}
			// hasher.update();
			// hasher.setEntities(entities);
		}
	}

	public void load() {
		entities = (ArrayList<Entity>) level.getEntities();

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Player) {
				p = (Player) entities.get(i);
				entities.set(i, p);
			}
			entities.get(i).fetchImage();
			entities.get(i).setAffineTransform();
		}

		hasher.setEntities(entities);
	}

	public void updateCoins() {
		for (Entity e : entities) {
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

		if (e.getKeyCode() == 39) {
			try {
				bg.setBackground(1);
			} catch (InvalidBackgroundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getKeyCode() == 37) {
			try {
				bg.setBackground(0);
			} catch (InvalidBackgroundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getKeyCode() == 82) {
			for (Entity en : entities) {
				en.reset();
			}
		}

		if (e.getKeyCode() == 77) {
			level.save();
		}

		if (e.getKeyCode() == 78) {
			level = new Level(Level.generateName());
			load();
		}

		if(e.getKeyCode() == 49) {
			objType = 0; // Ball
		}
		if(e.getKeyCode() == 50) {
			objType = 1; // Coin
		}
		if(e.getKeyCode() == 51) {
			objType = 2; // Wall
		}
		if(e.getKeyCode() == 52) {
			objType = 3; // SafeZone
		}

		if (e.getKeyCode() == 53) {
			objType = 4; //Player
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
		// if (e.getButton() == MouseEvent.BUTTON1) {
		// 	if (objType == 0) {
		// 		level.addEntity(new Ball(e.getX()-20, e.getY()-20, 5, 5, 15));
		// 	}
		// 	if (objType == 1) {
		// 		level.addEntity(new Coin(e.getX()-20, e.getY()-20, 0, 0, 15));
		// 	}
		// 	if (objType == 2) {
		// 		level.addEntity(new Barrier(e.getX()-20, e.getY()-20, 0, 0, 25, 5));
		// 	}
		// 	if (objType == 3) {
		// 		level.addEntity(new SafeZone(e.getX()-20, e.getY()-20, 0, 0, 15,15, false));
		// 	}
		// 	if (objType == 4) {
		// 		if (!hasPlayer()) {
		// 			p = new Player(e.getX()-20, e.getY()-20);
		// 			level.addEntity(p);
		// 		}
		// 	}
		// }

		if (e.getButton() == MouseEvent.BUTTON1) {
			// LevelEditor lvlEditor = new LevelEditor();
			Point p = e.getPoint();

			if (editor_hitbox.contains(p)) {
				try {
					LevelEditor lvlEditor = new LevelEditor();
				} catch (InvalidBackgroundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		// if (e.getButton() == MouseEvent.BUTTON3) {
		// 	entities.remove(entities.size()-1);
		// }
		if (e.getButton() == MouseEvent.BUTTON1) {}
		
	}

	public boolean hasPlayer() {
		for (Entity e : entities) {
			if (e instanceof Player)
				return true;
		}
		return false;
	}

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

enum Mode {
	EDITOR,
	PLAYING
}