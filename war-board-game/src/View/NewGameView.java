package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NewGameView extends JFrame implements ActionListener {

    private static final long serialVersionUID = -2276254397310425404L;
    private static final int DEFAULT_WIDTH = 396;
	private static final int DEFAULT_HEIGHT = 650;
    private final JPanel panel = new NewGamePanel();
    private final JButton newGameButton = new JButton();
    
    NewGameView() {
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = screenSize.width/2 - DEFAULT_WIDTH/2;
        int y = screenSize.height/2 - DEFAULT_HEIGHT/2;
        
        setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT + 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setTitle("WAR - Novo Jogo");
        getContentPane().setLayout(null);

        panel.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        setupButton();
        add(panel);
    }

    private void setupButton() {
        newGameButton.setOpaque(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setBounds(13, 45, 205, 60);
        newGameButton.addActionListener(this);
        add(newGameButton);
    }

    public static void main(String[] args) {
        JFrame frame = new NewGameView();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Botao pressionado novo jogo");
    }
}

class NewGamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage newGameImage;

    NewGamePanel() {
        try {
            newGameImage = ImageIO.read(getClass().getResource("/images/bgconfiguracao.png"));
        } catch(IOException e) {
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(newGameImage, 0, 0, null);
    }
}