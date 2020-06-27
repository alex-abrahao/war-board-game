package View;

import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import Model.PlayerColor;
import Model.Territories;
import Model.observer.UnitNumberObserver;

import java.awt.*;

public class UnitsLabel extends JPanel implements UnitNumberObserver {

    private static final long serialVersionUID = 4587872428157231272L;
    private JLabel numberLabel = new JLabel("0", JLabel.CENTER);
    private final Territories associatedTerritory;
    private final UnitsLabelDelegate delegate;

    public UnitsLabel(Territories associatedTerritory, UnitsLabelDelegate delegate) {
        this.delegate = delegate;
        this.associatedTerritory = associatedTerritory;
        this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        numberLabel.setForeground(Color.black);
        numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
        this.add(numberLabel);
        this.setSize(30, 30);
        setOpaque(false);
        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pressed();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }

    public void setColors(Color labelColor) {
        if (labelColor == Color.white || labelColor == Color.yellow || labelColor == Color.green) {
            this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        } else {
            this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        }
        numberLabel.setForeground(labelColor);
    }

    public void setNumberOfUnits(int number) {
        numberLabel.setText(String.valueOf(number));
    }

    private void pressed() {
        System.out.println("Selecionado " + associatedTerritory.getName());
        if (delegate != null) {
            delegate.didSelectLabel(associatedTerritory);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(null);
        UnitsLabel label = new UnitsLabel(Territories.Angola, null);
        label.setColors(Color.yellow);
        label.setNumberOfUnits(30);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(label);

        frame.setSize(100, 100);
        frame.setVisible(true);
    }

    @Override
    public void notify(int units, PlayerColor color) {
        setNumberOfUnits(units);
        Color labelColor = color.getColor();
        setColors(labelColor);
    }
}