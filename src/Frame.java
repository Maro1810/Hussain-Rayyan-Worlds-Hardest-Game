import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JPanel implements KeyListener, ActionListener, MouseListener {
	
	Player p = new Player();

	BackGround bg = new BackGround();

	Ball b = new Ball(200, 200, 1, 0, 20);
	Ball b2 = new Ball(400, 200, -1, 0, 20);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
		
		
	}
	
	public Frame() {
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
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);

		bg.paint(g);
		b.paint(g);
		b2.paint(g);
		p.paint(g);

		b.collision(p);
		b.collision(b2);
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