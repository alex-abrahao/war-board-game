package View;

import java.awt.Color;

import javax.swing.*;

public class UnitsLabel extends JPanel {

    private static final long serialVersionUID = 4587872428157231272L;
    private JLabel numberLabel = new JLabel("0", JLabel.CENTER);

    public UnitsLabel() {
        this.setBackground(Color.white);
        numberLabel.setForeground(Color.black);
        this.add(numberLabel);
    }

    public void setColors(Color labelColor, Color backgroundColor) {
        this.setBackground(backgroundColor);
        numberLabel.setForeground(labelColor);
    }

    public void setNumberOfUnits(int number) {
        numberLabel.setText(String.valueOf(number));
    }
}