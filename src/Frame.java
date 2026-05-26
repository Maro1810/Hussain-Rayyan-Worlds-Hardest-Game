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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener{
	
	Player p = new Player(100, 100);
	BackGround bg;

	ArrayList<Entity> entities = new ArrayList<>();

	SpatialHasher hasher = new SpatialHasher(entities, 1000);

	Level level = Level.load("Hi.json");

	Button editor = new Button("/imgs/LvlBuilderButton.png", 530, 450, 5);
	Button play = new Button("/imgs/PlayButton.png", 380, 450, 5);

	Rectangle editor_hitbox = new Rectangle(530, 450, 90, 90);
	Rectangle play_hitbox = new Rectangle(380, 450, 90, 90);

	JFrame menu;

	JComboBox<String> dropdown;

	public static Mode mode = Mode.PLAYING;
	
	int coins = 0;
	int objType = 0;

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException, InvalidBackgroundException {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
	}
	
	public Frame() throws FileNotFoundException, IOException, ClassNotFoundException, InvalidBackgroundException {
		menu = new JFrame("Main Menu");

		ArrayList<String> levelList = new ArrayList<>();

		levelList.add("Select a level");

		addOptions(levelList);

		dropdown = new JComboBox<>(levelList.toArray(new String[0]));

		this.setLayout(null);
		this.setFocusable(true);

		this.setSize(new Dimension(1040, 739));

		dropdown.setBounds(430, 570, 150, 40);

		menu.setSize(new Dimension(1040, 739));
		menu.setBackground(Color.white);
		menu.add(this);
		menu.addMouseListener(this);

		this.add(dropdown);

		dropdown.addKeyListener(this);
		this.addKeyListener(this);
		menu.addKeyListener(this);

		
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer t = new Timer(16, this);
		t.start();
		
		bg = new BackGround(0);

		menu.setVisible(true);

		load();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.RED);

		bg.paint(g);

		if(bg.getScreen() == 0) {
			editor.paint(g);
			play.paint(g);
		}

		if (bg.getScreen() == 1) {
			hasher.update();

			level.paint(g);
			dropdown.setVisible(false);
		}
		win();
		reset();
			
	}
	public void reset() {
		if (p.isDead()) {
			for (Entity e : entities) {
				e.reset();
			}
		}
	}

	public boolean allCoinsCollected() {
		for (Entity e : entities) {
			if (e instanceof Coin) {
				Coin c = (Coin) e;

				if (!c.isCollected()) {
					return false;
				}
			}
		}
		return true;
	}

	public void addOptions(ArrayList<String> levels) {
		File file = new File("src/levels");

        String[] names = file.list();

        for (int i = 0; i < names.length; i++) {
			levels.add(names[i]);
		}
	}

	public void win() {
		if (p.winning() && allCoinsCollected()) {
			System.out.println("you win!");
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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87) {
			p.setVy(-7);
		}
		if(e.getKeyCode() == 83) {
			p.setVy(7);
		}
		if(e.getKeyCode() == 65) {
			p.setVx(-7);
		}
		if(e.getKeyCode() == 68) {
			p.setVx(7);
		}

		if (e.getKeyCode() == 82) {
			for (Entity en : entities) {
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
		// TODO Auto-generated method stubbed

		if (e.getButton() == MouseEvent.BUTTON1) {
			Point p = e.getPoint();

			if (editor_hitbox.contains(p) && bg.getScreen() == 0) {
				try {
					LevelEditor lvlEditor = new LevelEditor();

					menu.dispose();
				} catch (InvalidBackgroundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (play_hitbox.contains(p) && bg.getScreen() == 0) {
				try {
					if (dropdown.getSelectedItem().toString().equals("Select a level")) {
						level = Level.load("Hi.json");
					}
					else {
						level = Level.load(dropdown.getSelectedItem().toString());
					}
					load();
					
					bg.setBackground(1);
				} catch (InvalidBackgroundException e1) {
					e1.printStackTrace();
				}
				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
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