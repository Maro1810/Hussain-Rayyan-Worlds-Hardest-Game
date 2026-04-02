import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Runner extends JPanel implements KeyListener {
	
	Player p = new Player();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame menu = new JFrame("Main Menu");
		Runner panel = new Runner();
		menu.add(panel);
		menu.setSize(1000, 1000);
		menu.setVisible(true);
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		p.paint(g);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 87) {
			p.setvy(-3);
			repaint();
		}
		if(e.getKeyCode() == 83) {
			p.setvy(3);
			repaint();
		}
		if(e.getKeyCode() == 65) {
			p.setvx(-3);
			repaint();
		}
		if(e.getKeyCode() == 68) {
			p.setvx(3);
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		p.setvx(0);
		p.setvy(0);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
