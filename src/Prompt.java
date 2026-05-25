import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Prompt extends JDialog implements KeyListener, ActionListener{
    JTextField xField, yField;
    JTextArea xDesc, yDesc;

    private int xParam, yParam;

    public Prompt(EntityType type) {
        setModal(true);
        
        xField = new JTextField(5);
        yField = new JTextField(5);

        if (type == EntityType.BALL) {
            xDesc = new JTextArea("vx");
            yDesc = new JTextArea("vy");
        }
        else {
            xDesc = new JTextArea("width");
            yDesc = new JTextArea("height");
        }

        this.setLayout(null);
        this.setSize(new Dimension(200, 200));

        xField.setBounds(20, 20, 50, 20);
        yField.setBounds(80, 20, 50, 20);

        xDesc.setBounds(20, 50, 50, 50);
        yDesc.setBounds(80, 50, 50, 50);

        xDesc.setBackground(Color.WHITE);
        yDesc.setBackground(Color.WHITE);
    
        xField.addKeyListener(this);
        yField.addKeyListener(this);

        this.add(xField);
        this.add(yField);

        this.add(xDesc);
        this.add(yDesc);

        this.setBackground(Color.WHITE);

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
            this.xParam = (int) (Double.parseDouble(xField.getText()));
            this.yParam = (int) (Double.parseDouble(yField.getText()));

            this.dispose();
        }
    }

    public int getXParam() {
        return xParam;
    }

    public int getYParam() {
        return yParam;
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
