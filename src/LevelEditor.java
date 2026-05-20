import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

    Barrier barrier = new Barrier(50, 50, 50, 50);

    public LevelEditor() throws InvalidBackgroundException {
        JFrame frame = new JFrame("Level Editor");

        bg = new BackGround(1);

        frame.setSize(new Dimension(1000, 750));
        frame.setBackground(Color.white);
        frame.add(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.setVisible(true);

        Timer t = new Timer(16, this);

        t.start();
    }

    @Override
    public void paint(Graphics g) {
        bg.paint(g);
        barrier.paint(g);
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("hi");
        }

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
}
