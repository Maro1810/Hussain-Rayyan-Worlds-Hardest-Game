import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Prompt extends JDialog implements KeyListener, ActionListener{
    JTextField xField, yField;

    private int xLength, yLength;

    public Prompt() {
        setModal(true);
        
        xField = new JTextField(5);
        yField = new JTextField(5);

        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(200, 200));

        xField.setBounds(20, 20, 50, 20);
        yField.setBounds(80, 20, 50, 20);
    

        xField.addKeyListener(this);
        yField.addKeyListener(this);

        this.add(xField);
        this.add(yField);

        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == 10) {
            this.xLength = (int) (Double.parseDouble(xField.getText()));
            this.yLength = (int) (Double.parseDouble(yField.getText()));

            this.dispose();
        }
        System.out.println("hi");
    }

    public int getXLength() {
        return xLength;
    }

    public int getYLength() {
        return yLength;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        repaint();
    }
}
