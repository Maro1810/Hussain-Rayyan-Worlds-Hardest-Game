import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class LevelEditor extends JPanel implements MouseListener, ActionListener{

    JFrame frame;

    BackGround bg;
    public static int objType = 0;

    Palette palette = new Palette();

    ArrayList<Entity> removedEntities = new ArrayList<>();

    Level level;
    int currPath = 0;

    public LevelEditor() throws InvalidBackgroundException {

        frame = new JFrame("Level Editor");
        Frame.mode = Mode.EDITOR;

        Prompt prompt = new Prompt(4);

        if (prompt.getName().equals("")) {
            level = new Level(Level.generateName());
        }

        else {
            String name = prompt.getName();
            
            level = new Level(name);
        }

        bg = new BackGround(1);

        frame.setSize(new Dimension(1040, 739));
        frame.setBackground(Color.white);
        frame.add(this);
        frame.addMouseListener(this);
 
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        Timer t = new Timer(16, this);

        t.start();
    }

    public static void main(String... args) throws InvalidBackgroundException {
        LevelEditor lv = new LevelEditor();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        bg.paint(g);
        level.paint(g);

        g.setColor(Color.black);
        drawGridLines(g);
        
        if (palette.disposed) {
            frame.dispose();
            Frame.mode = Mode.PLAYING;
        }

        if (palette.undo) {
            undo();
            palette.undo = false;
        }

        if (palette.redo) {
            redo();
            palette.redo = false;
        }

        if (palette.save) {
            level.save();
            palette.save = false;
        }
    }

    public boolean canBePlaced(Entity e) {
        for (Entity entity : level.getEntities()) {
            if (e.getHitbox().intersects(entity.getHitbox())) {
                return false;
            }
        }

        if (e instanceof Player) {
            Player p = (Player) e;

            if (p.getX() <= 0 || p.getY() <= 0 || p.getX()+40.5 >= 1030 || p.getY() >= 730) {
                return false;
            }
        }

        if (e instanceof Ball) {
            Ball b = (Ball) e;

            if (b.getX() <= 0 || b.getY() <= 0 || b.getX() + b.getHitbox().getWidth() >= 1030 || b.getY() + b.getHitbox().getWidth() >= 710) {
                return false;
            }
        }
        return true;
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
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getButton() == MouseEvent.BUTTON1) {
			if (objType == 0) {
                Prompt prompt1 = new Prompt(0);
                
                String str = prompt1.getDropdownString();

                if (str.equals("FREE")) {
                    Prompt prompt2 = new Prompt(1);

                    int vx = prompt2.getXParam();
                    int vy = prompt2.getYParam();
                    int size = prompt2.getSizeParam();

                    if (canBePlaced(new Ball(e.getX()-10, e.getY()-32, vx, vy, size))) 
                        level.addEntity(new Ball(e.getX()-10, e.getY()-32, vx, vy, size));           
                }
                else {
                    Prompt prompt3 = new Prompt(2);

                    int x_radius = prompt3.getXParam();
                    int y_radius = prompt3.getYParam();
                    int v = prompt3.getVParam();
                    int size = prompt3.getSizeParam();

                    if (canBePlaced(new Ball(e.getX()-60, e.getY()-80, x_radius, y_radius, v, size))) 
                        level.addEntity(new Ball(e.getX()-60, e.getY()-80, x_radius, y_radius, v, size));
                    
                }
			}
			if (objType == 1) {
				
                if (canBePlaced(new Coin(e.getX()-10, e.getY()-32, 0, 0, 15)))
				    level.addEntity(new Coin(e.getX()-10, e.getY()-32, 0, 0, 15));
			}
			if (objType == 2) {
				
                int[] coords = snappedCoordinates(e.getX(), e.getY());

                if (!level.containsEntity(coords[0], coords[1])) {
                    Prompt prompt = new Prompt(5);

                    int xLength = prompt.getXParam();
                    int yLength = prompt.getYParam();
                    
                    if (canBePlaced(new Barrier(coords[0], coords[1], xLength, yLength)))
				        level.addEntity(new Barrier(coords[0], coords[1], xLength, yLength));
                }

			}
			if (objType == 3) {
				
                int[] coords = snappedCoordinates(e.getX(), e.getY());

                if (!level.containsEntity(coords[0], coords[1])) {
                    Prompt prompt = new Prompt(3);

                    int xLength = prompt.getXParam();
                    int yLength = prompt.getYParam();

                    boolean end = (prompt.getDropdownString() == "End" ? true : false);

                    if (canBePlaced(new SafeZone(coords[0], coords[1], xLength, yLength, end))) 
                        level.addEntity(new SafeZone(coords[0], coords[1], xLength, yLength, end));       
                }
			}

            if (objType == 4) {
                if (!hasPlayer()) 
                    if (canBePlaced(new Player(e.getX()-10, e.getY()-32)))
                        level.addEntity(new Player(e.getX()-10, e.getY()-32));

			}
        }

    }

    public void undo() {
        if (level.getEntities().size() > 0) {
            removedEntities.add(level.getEntities().remove(level.getEntities().size()-1));
        }
    }

    public boolean hasPlayer() {
		for (Entity e : level.getEntities()) {
			if (e instanceof Player)
				return true;
		}
		return false;
	}

    public void redo() {
        if (removedEntities.size() > 0) {
            level.getEntities().add(removedEntities.remove(removedEntities.size()-1));
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
