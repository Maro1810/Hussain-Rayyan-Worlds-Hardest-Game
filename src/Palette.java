import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.css.Rect;

public class Palette extends JPanel implements MouseListener, ActionListener{

    JFrame palette;

    boolean disposed = false;

    Button ball = new Button("/imgs/Ball.png", 20, 20);
    Button player = new Button("/imgs/Player.png", 100, 20);
    Button safeZone = new Button("/imgs/SafeZone.png", 20, 100);
    Button barrier = new Button("/imgs/Barrier.png", 20, 250);
    Button coin = new Button("/imgs/Coin.png", 70, 410);
    Button home = new Button("/imgs/HomeButton.png", 50, 600);

    Rectangle ballHitbox = new Rectangle(20, 20, 45, 45);
    Rectangle playerHitbox = new Rectangle(100, 20, 45, 45);
    Rectangle safeZoneHitbox = new Rectangle(20, 100, 137, 137);
    Rectangle barrierHitbox = new Rectangle(20, 250, 137, 137);
    Rectangle coinHitbox = new Rectangle(70, 410, 35, 45);
    Rectangle homeHitbox = new Rectangle(50, 600, 90, 90);

    Point currPoint = new Point(0, 0);

    private boolean drawBallHitbox, 
                    drawSafeZoneHitbox, 
                    drawPlayerHitbox, 
                    drawBarrierHitbox, 
                    drawCoinHitbox;

    public Palette() {
        palette = new JFrame("Palette");

        palette.setSize(new Dimension(200, 739));
        palette.add(this);
        palette.addMouseListener(this);

        palette.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        palette.setVisible(true);

        palette.setLocation(1050, 0);

        Timer t = new Timer(16, this);

        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(Color.gray);

        ball.paint(g);
        player.paint(g);
        safeZone.paint(g);
        barrier.paint(g);
        coin.paint(g);
        home.paint(g);

        g.setColor(Color.blue);

        if (drawBallHitbox) {
            g.drawRect(ballHitbox.x, ballHitbox.y, ballHitbox.width, ballHitbox.height);
        }

        if (drawPlayerHitbox) {
            g.drawRect(playerHitbox.x, playerHitbox.y, playerHitbox.width, playerHitbox.height);
        }
        
        if (drawSafeZoneHitbox) {
            g.drawRect(safeZoneHitbox.x, safeZoneHitbox.y, safeZoneHitbox.width, safeZoneHitbox.height);
        }

        if (drawBarrierHitbox) {
            g.drawRect(barrierHitbox.x, barrierHitbox.y, barrierHitbox.width, barrierHitbox.height);
        }

        if (drawCoinHitbox) {
            g.drawRect(coinHitbox.x, coinHitbox.y, coinHitbox.width, coinHitbox.height);
        }

        g.drawOval((int) currPoint.getX()-10, (int) currPoint.getY()-32, 5, 5);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println(e.getPoint());
            Point p = new Point((int) e.getPoint().getX()-10, (int) e.getPoint().getY()-32);

            System.out.println(ballHitbox.contains(p));

            if (ballHitbox.contains(p)) {
                drawBallHitbox = true;

                drawPlayerHitbox = drawSafeZoneHitbox = drawCoinHitbox = drawBarrierHitbox = false;

                LevelEditor.objType = 0;

            }

            else if (playerHitbox.contains(p)) {
                drawPlayerHitbox = true;

                drawBallHitbox = drawSafeZoneHitbox = drawCoinHitbox = drawBarrierHitbox = false;

                LevelEditor.objType = 4;
            }

            else if (safeZoneHitbox.contains(p)) {
                drawSafeZoneHitbox = true;

                drawBallHitbox = drawPlayerHitbox = drawCoinHitbox = drawBarrierHitbox = false;

                LevelEditor.objType = 3;
            }

            else if (barrierHitbox.contains(p)) {
                drawBarrierHitbox = true;

                drawBallHitbox = drawPlayerHitbox = drawSafeZoneHitbox = drawCoinHitbox = false;

                LevelEditor.objType = 2;
            }

            else if (coinHitbox.contains(p)) {
                drawCoinHitbox = true;

                drawBallHitbox = drawPlayerHitbox = drawSafeZoneHitbox = drawBarrierHitbox = false;

                LevelEditor.objType = 1;
            }
            else if (homeHitbox.contains(p)) {
                disposed = true;
                palette.dispose();
                try {
                    Frame frame = new Frame();
                } catch (ClassNotFoundException | IOException | InvalidBackgroundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
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
