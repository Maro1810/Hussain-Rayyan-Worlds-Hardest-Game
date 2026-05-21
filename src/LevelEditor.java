import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelEditor extends JPanel implements MouseListener, KeyListener, ActionListener{
    
    BackGround bg;
    int objType = 0;

    SafeZone zone = new SafeZone(0, 0, 13.9, 13.9, false);
    Level level;

    public LevelEditor() throws InvalidBackgroundException {
        JFrame frame = new JFrame("Level Editor");

        level = new Level(Level.generateName());

        bg = new BackGround(1);

        frame.setSize(new Dimension(1040, 739));
        frame.setBackground(Color.white);
        frame.add(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);

        System.out.println(frame.getContentPane().getSize());
        
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.setVisible(true);

        Timer t = new Timer(16, this);

        t.start();
    }

    public static void main(String... args) throws InvalidBackgroundException {
        LevelEditor lv = new LevelEditor();
    }

    @Override
    public void paint(Graphics g) {
        bg.paint(g);
        zone.paint(g);
        level.paint(g);

        g.setColor(Color.black);
        drawGridLines(g);

        // g.drawRect(0, 0, 17, 17);
    }

    public void drawGridLines(Graphics g) {
        for (int i = 0; i < 37; i++) {
            g.drawLine(27+i*27, 0, 27+i*27, 800);
        }

        for (int i = 0; i < 27; i++) {
            g.drawLine(0, 27+i*27, 2000, 27+i*27);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        repaint();
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
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
			if (objType == 0) {
				level.addEntity(new Ball(e.getX()-20, e.getY()-20, 5, 5, 15));
			}
			if (objType == 1) {
				level.addEntity(new Coin(e.getX()-20, e.getY()-20, 0, 0, 15));
			}
			if (objType == 2) {
                int[] coords = snappedCoordinates(e.getX(), e.getY());

				level.addEntity(new Barrier(coords[0], coords[1], 17, 17));
			}
			if (objType == 3) {
                int[] coords = snappedCoordinates(e.getX(), e.getY());

				level.addEntity(new SafeZone(coords[0], coords[1], 13.9,13.9, false));
			}
        }

    }

    public int[] snappedCoordinates(int x, int y) {
        int[] coords = new int[2];

        coords[0] = (x/27)*27;
        coords[1] = ((y/27)*27)-27;

        return coords;
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
}
