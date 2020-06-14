package View;

import java.awt.Color;

import javax.swing.*;
import java.awt.*;

public class UnitsLabel extends JPanel {

    private static final long serialVersionUID = 4587872428157231272L;
    private JLabel numberLabel = new JLabel("0", JLabel.CENTER);

    public UnitsLabel() {
        this.setBackground(Color.white);
        numberLabel.setForeground(Color.black);
        numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
        this.add(numberLabel);
        this.setSize(30, 30);
    }

    public void setColors(Color labelColor) {
        if (labelColor == Color.white || labelColor == Color.yellow) {
            this.setBackground(new Color(0, 0, 0, 0.5f));
        } else {
            this.setBackground(new Color(255, 255, 255, 0.5f));
        }
        numberLabel.setForeground(labelColor);
    }

    public void setNumberOfUnits(int number) {
        numberLabel.setText(String.valueOf(number));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(null);
        UnitsLabel label = new UnitsLabel();
        label.setColors(Color.yellow);
        label.setNumberOfUnits(30);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(label);

        frame.setSize(100, 100);
        frame.setVisible(true);
    }
}