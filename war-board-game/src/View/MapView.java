package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.awt.Graphics;

public class MapView extends JPanel {

    private static final long serialVersionUID = -8933186418761493148L;
    private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 785;
    private BufferedImage mapImage;
    private BufferedImage backgroundImage;

    public MapView() {
        try {
            mapImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_mapa copy.png"));
            backgroundImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_fundo.png"));
        }
        catch(IOException e) {
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    @Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(mapImage, 0, 0, null);
	}

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = screenSize.width/2 - DEFAULT_WIDTH/2;
        int y = screenSize.height/2 - DEFAULT_HEIGHT/2;
        
        frame.setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MapView());
        frame.setVisible(true);
        frame.setTitle("WAR");
     }
}