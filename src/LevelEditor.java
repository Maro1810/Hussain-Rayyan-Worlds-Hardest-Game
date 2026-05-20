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

    Barrier barrier = new Barrier(0, 0, 17, 17);

    public LevelEditor() throws InvalidBackgroundException {
        JFrame frame = new JFrame("Level Editor");

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
        barrier.paint(g);

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
