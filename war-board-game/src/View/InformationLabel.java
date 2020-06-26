package View;

import java.awt.*;

import javax.swing.*;

import Model.observer.StringObserver;

public class InformationLabel extends JPanel implements StringObserver {

    private static final long serialVersionUID = 1400198522438442351L;
    private JLabel textLabel = new JLabel("0", JLabel.CENTER);

    InformationLabel() {
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.5f));
        textLabel.setForeground(Color.white);
        textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD));
        this.add(textLabel);
        this.setSize(200, 30);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }

    @Override
    public void notify(String value) {
        textLabel.setText(value);
    }
}