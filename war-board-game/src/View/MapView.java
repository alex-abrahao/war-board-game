package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapView extends JPanel {

    private static final long serialVersionUID = -8933186418761493148L;
    private static final int DEFAULT_WIDTH = 1024;
	private static final int DEFAULT_HEIGHT = 785;
    private BufferedImage mapImage;
    private BufferedImage backgroundImage;
    private JButton throwDiceButton = new JButton(new ImageIcon(getClass().getResource("/images/war_btnJogarDados.png")));
    private JButton nextRoundButton = new JButton(new ImageIcon(getClass().getResource("/images/war_btnProxJogada.png")));

    public MapView() {
        try {
            mapImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_mapa copy.png"));
            backgroundImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_fundo.png"));
        } catch(IOException e) {
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
        setLayout(null);
        setupButtons();
    }

    private void setupButtons() {
        throwDiceButton.setBounds(15, DEFAULT_HEIGHT - 115, 65, 65);
        throwDiceButton.setBorder(BorderFactory.createEmptyBorder());
        nextRoundButton.setBounds(90, DEFAULT_HEIGHT - 115, 65, 65);
        nextRoundButton.setBorder(BorderFactory.createEmptyBorder());
        
        throwDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                didPressThrowDice();
            }
        });

        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                didPressNextRound();
            }
        });

        add(throwDiceButton);
        add(nextRoundButton);
    }

    private void didPressNextRound() {
        System.out.println("Nova rodada");
    }

    private void didPressThrowDice() {
        System.out.println("Jogar dados");
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
        frame.setResizable(false);
        frame.getContentPane().add(new MapView());
        frame.setVisible(true);
        frame.setTitle("WAR");
    }
}