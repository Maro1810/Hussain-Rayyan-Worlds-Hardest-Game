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

public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener {
	
	Player p = new Player();

	BackGround bg = new BackGround();

	ArrayList<Entity> entities = new ArrayList<>();

	ArrayList<Entity> deserializedEntities = new ArrayList<>();

	Ball b = new Ball(200, 200, 1, 0, 20);
	Ball b2 = new Ball(400, 200, -1, 0, 20);

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
		
		
	}
	
	public Frame() throws FileNotFoundException, IOException, ClassNotFoundException {
		JFrame menu = new JFrame("Main Menu");
		menu.setSize(new Dimension(1000, 1000));
		menu.setBackground(Color.white);
		menu.add(this);
		menu.addMouseListener(this);
		menu.addKeyListener(this);
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer t = new Timer(16, this);
		t.start();
		
		menu.setVisible(true);


        Ball ball1 = new Ball(1, 1, 1, 10, 15);
        Ball ball2 = new Ball(20, 20, 10, 0, 20);
        Player player = new Player();

        entities.add(ball1);
        entities.add(ball2);
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
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);

		bg.paint(g);

		for (Entity e : deserializedEntities) {
			e.paint(g);
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