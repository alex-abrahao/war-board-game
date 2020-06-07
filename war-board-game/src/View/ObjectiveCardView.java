package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class ObjectiveCardView extends JPanel {

    private static final long serialVersionUID = -4699262881138583669L;
    private static final int DEFAULT_WIDTH = 353;
	private static final int DEFAULT_HEIGHT = 581;
    private JTextArea descriptionLabel = new JTextArea("Objetivo");
    private BufferedImage cardImage;

    ObjectiveCardView() {
        try {
            cardImage = ImageIO.read(getClass().getResource("/images/war_carta_objetivo_grande.png"));
        }
        catch(IOException e) {
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setLayout(null);
        descriptionLabel.setBounds(40, 130, DEFAULT_WIDTH - 80, 300);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        descriptionLabel.setEditable(false);  
        descriptionLabel.setCursor(null);  
        descriptionLabel.setOpaque(false);  
        descriptionLabel.setFocusable(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        add(descriptionLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(cardImage, 0, 0, null);
    }

    void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Objetivo");
        ObjectiveCardView view = new ObjectiveCardView();
        view.setDescription("Objetivo grande bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla");
        frame.getContentPane().add(view);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}