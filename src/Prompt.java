import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Prompt extends JDialog implements KeyListener, ActionListener {
    JTextField xField, yField, vField;
    JTextArea xDesc, yDesc, vDesc;

    private int xParam, yParam, vParam;
    private JComboBox<String> dropdown;

    int num;

    public Prompt(int num) {
        setModal(true);
        this.num = num;
        
        xField = new JTextField(5);
        yField = new JTextField(5);

        if (num == 0) {
            String[] options = {"FREE", "LOOP"};

            dropdown = new JComboBox<>(options);

            dropdown.setBounds(40, 20, 100, 50);

            xDesc = new JTextArea("(click x to continue)");

            xDesc.setEditable(false);

            this.add(dropdown);
            dropdown.setVisible(true);
        }
        else if (num == 1) {
            xDesc = new JTextArea("vx");
            yDesc = new JTextArea("vy");

            xDesc.setEditable(false);
            yDesc.setEditable(false);

        }
        else if (num == 2) {
            xDesc = new JTextArea("x-radius");
            yDesc = new JTextArea("y-radius");

            vDesc = new JTextArea("velocity");

            vField = new JTextField(5);

            xDesc.setEditable(false);
            yDesc.setEditable(false);
            vDesc.setEditable(false);
        }

        else if (num == 3) {
            String[] options = {"Checkpoint", "End"};

            dropdown = new JComboBox<>(options);

            dropdown.setBounds(40, 100, 100, 50);

            xDesc = new JTextArea("width");
            yDesc = new JTextArea("height");

            xDesc.setEditable(false);
            yDesc.setEditable(false);

            this.add(dropdown);

            dropdown.setVisible(true);
        }
        else if (num == 4) {
            xField = new JTextField(5);
            xDesc = new JTextArea("Enter a name (or leave blank)");

            xDesc.setEditable(true);
        }
        else {
            xDesc = new JTextArea("width");
            yDesc = new JTextArea("height");

            xDesc.setEditable(false);
            yDesc.setEditable(false);
        }

        this.setLayout(null);
        this.setSize(new Dimension(200, 200));


        if (num != 0 && num != 2 && num != 4) {
            xDesc.setBounds(20, 50, 50, 50);
            yDesc.setBounds(80, 50, 50, 50);

            xField.setBounds(20, 20, 50, 20);
            yField.setBounds(80, 20, 50, 20);

            xDesc.setBackground(Color.WHITE);
            yDesc.setBackground(Color.WHITE);
    
            xField.addKeyListener(this);
            yField.addKeyListener(this);

            this.add(xField);
            this.add(yField);

            this.add(xDesc);
            this.add(yDesc);
        }

        if (num == 0) {
            xDesc.setBounds(30, 80, 120, 50);

            this.add(xDesc);

        }

        if (num == 4) {
            xDesc.setBounds(20, 50, 160, 20);
            xField.setBounds(20, 20, 140, 20);

            xField.addKeyListener(this);

            this.add(xField);
            this.add(xDesc);
        }

        if (num == 2) {
            xField.setBounds(20, 20, 50, 20);
            yField.setBounds(80, 20, 50, 20);
            vField.setBounds(50, 100, 50, 20);

            xDesc.setBounds(20, 50, 50, 50);
            yDesc.setBounds(80, 50, 50, 50);

            vDesc.setBounds(50, 120, 50, 20);

            this.add(xDesc);
            this.add(yDesc);
            this.add(vDesc);

            this.add(xField);
            this.add(yField);
            this.add(vField);

            xField.addKeyListener(this);
            yField.addKeyListener(this);
            vField.addKeyListener(this);
        }

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

            if (num != 0) {
                if (isParsable(xField.getText())) {
                    this.xParam = (int) (Double.parseDouble(xField.getText()));
                }
                else {
                    this.xParam = 0;
                }
                
                if (isParsable(yField.getText())) {
                    this.yParam = (int) (Double.parseDouble(yField.getText()));
                }
                else {
                    this.yParam = 0;
                }
            }
            if (num == 2) {

                if (isParsable(vField.getText())) {
                    this.vParam = (int) (Double.parseDouble(vField.getText()));
                }

                else {
                    this.vParam = 0;
                }
                
            }

            this.dispose();
        }
    }

    public String getDropdownString() {
        if (num == 0 || num == 3) {
            return dropdown.getSelectedItem().toString();
        }
        return "";
    }

    public int getXParam() {
        return xParam;
    }

    public String getName() {
        return xField.getText();
    }

    public int getYParam() {
        return yParam;
    }

    public int getVParam() {
        return vParam;
    }

    public boolean isParsable(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
