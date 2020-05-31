package View;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class mapView extends JPanel{

    private BufferedImage mapImage;

    public mapView(){
        try {
            mapImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_mapa copy.png"));
        }
        catch(IOException e){
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
    }
    public BufferedImage getMapImage(){
        return mapImage;
    }
    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mapImage, 0, 0, null);
	}

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new mapView());
  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 785);
        frame.setVisible(true);
     }
}