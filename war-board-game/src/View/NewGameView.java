package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.NewGameController;

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
    private final JButton loadGameContinentsButton = new JButton("Carregar Conq Continentes");
    private final JButton loadGame24TerritoriesButton = new JButton("Carregar Conq de 24 Terr");
    private final JButton loadGameDefeatPlayerButton = new JButton("Carregar Destru Exército");
    private final JButton loadGameChangeCardsButton = new JButton("Carregar troca de cartas");


    private final NewGameController controller;
    
    public NewGameView(NewGameController controller) {
        this.controller = controller;
        this.controller.setView(this);
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = screenSize.width/2 - DEFAULT_WIDTH/2;
        int y = screenSize.height/2 - DEFAULT_HEIGHT/2;
        
        setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT + 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        loadGameContinentsButton.setBounds(13, 115, 205, 60);
        loadGameContinentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Botao pressionado load game");
                controller.didSelectLoadGame("saveData.dat");
            }
        });

        loadGame24TerritoriesButton.setBounds(13, 185, 205, 60);
        loadGame24TerritoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Botao pressionado load game");
                controller.didSelectLoadGame("saveData.dat");
            }
        });

        loadGameDefeatPlayerButton.setBounds(13, 255, 205, 60);
        loadGameDefeatPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Botao pressionado load game");
                controller.didSelectLoadGame("saveData.dat");
            }
        });

        loadGameChangeCardsButton.setBounds(13, 325, 205, 60);
        loadGameChangeCardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Botao pressionado load game");
                controller.didSelectLoadGame("saveData.dat");
            }
        });

        add(newGameButton);
        add(loadGameContinentsButton);
        add(loadGame24TerritoriesButton);
        add(loadGameDefeatPlayerButton);
        add(loadGameChangeCardsButton);
    
    }

    public static void main(String[] args) {
        NewGameController controller = new NewGameController();
        JFrame frame = new NewGameView(controller);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Botao pressionado novo jogo");
        controller.didSelectNewGame();
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