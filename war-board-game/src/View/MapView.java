package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import Controller.MapController;
import Model.PlayerColor;
import Model.PlayerInfo;
import Model.Territories;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapView extends JFrame {

    private static final long serialVersionUID = -8933186418761493148L;
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 785;
    private final MapController controller;
    private final MapPanel panel = new MapPanel();
    private JButton throwDiceButton = new JButton(new ImageIcon(getClass().getResource("/images/war_btnJogarDados.png")));
    private JButton nextRoundButton = new JButton(new ImageIcon(getClass().getResource("/images/war_btnProxJogada.png")));
    private JButton seeObjectiveButton = new JButton("Ver Objetivo");
    private JButton chooseDicesValue = new JButton("Escolher valor dos dados");

    public MapView(MapController controller) {
        this.controller = controller;
        this.controller.setView(this);
        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = screenSize.width/2 - DEFAULT_WIDTH/2;
        int y = screenSize.height/2 - DEFAULT_HEIGHT/2;
        setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("WAR");
        getContentPane().add(panel);
        setupButtons();
        addUnitLabels();
        addInformationLabels();
        this.controller.fetchPlayersOrder();
        this.controller.start();
    }

    private void setupButtons() {
        throwDiceButton.setBounds(15, DEFAULT_HEIGHT - 115, 65, 65);
        throwDiceButton.setBorder(BorderFactory.createEmptyBorder());
        nextRoundButton.setBounds(90, DEFAULT_HEIGHT - 115, 65, 65);
        nextRoundButton.setBorder(BorderFactory.createEmptyBorder());

        seeObjectiveButton.setBounds(DEFAULT_WIDTH - 120, 15, 100, 40);
        chooseDicesValue.setBounds(DEFAULT_WIDTH - 220, DEFAULT_HEIGHT - 90, 200, 40);

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

        seeObjectiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                didPressSeeObjective();
            }
        });

        chooseDicesValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                didPressChooseDicesValue();
            }
        });

        panel.add(throwDiceButton);
        panel.add(nextRoundButton);
        panel.add(seeObjectiveButton);
        panel.add(chooseDicesValue);
    }

    private void addInformationLabels() {
        controller.addCurrentPlayerObserver(makeInformationLabel(30, 16, 300, 28));
        controller.addCurrentStateObserver(makeInformationLabel(30, 53, 300, 28));
        controller.addMessageLabelObserver(makeInformationLabel(367, 16, 500, 28));
    }

    private InformationLabel makeInformationLabel(int x, int y, int width, int height) {
        InformationLabel label = new InformationLabel();
        label.setBounds(x, y, width, height);
        panel.add(label);
        return label;
    }

    private void addUnitLabels() {
        addUnitLabel(Territories.Alaska, 84, 129);
        addUnitLabel(Territories.Calgary, 165, 137);
        addUnitLabel(Territories.Vancouver, 151, 180);
        addUnitLabel(Territories.Greenland, 310, 100);
        addUnitLabel(Territories.Quebec, 273, 173);
        addUnitLabel(Territories.California, 106, 247);
        addUnitLabel(Territories.Texas, 167, 231);
        addUnitLabel(Territories.NewYork, 197, 257);
        addUnitLabel(Territories.Mexico, 130, 350);

        addUnitLabel(Territories.Venezuela, 188, 416);
        addUnitLabel(Territories.Brazil, 274, 442);
        addUnitLabel(Territories.Peru, 224, 482);
        addUnitLabel(Territories.Argentina, 258, 553);

        addUnitLabel(Territories.UnitedKingdom, 452, 174);
        addUnitLabel(Territories.France, 473, 238);
        addUnitLabel(Territories.Spain, 430, 274);
        addUnitLabel(Territories.Sweden, 517, 133);
        addUnitLabel(Territories.Italy, 526, 240);
        addUnitLabel(Territories.Romenia, 565, 259);
        addUnitLabel(Territories.Poland, 562, 190);
        addUnitLabel(Territories.Ukraine, 592, 221);

        addUnitLabel(Territories.Algeria, 454, 366);
        addUnitLabel(Territories.Nigeria, 524, 437);
        addUnitLabel(Territories.Egypt, 553, 375);
        addUnitLabel(Territories.Angola, 543, 497);
        addUnitLabel(Territories.SouthAfrica, 562, 558);
        addUnitLabel(Territories.Somalia, 603, 471);

        addUnitLabel(Territories.Perth, 794, 586);
        addUnitLabel(Territories.Australia, 861, 602);
        addUnitLabel(Territories.NewZealand, 920, 642);
        addUnitLabel(Territories.Indonesia, 885, 493);

        addUnitLabel(Territories.Letonia, 648, 181);
        addUnitLabel(Territories.Estonia, 665, 132);
        addUnitLabel(Territories.Russia, 768, 149);
        addUnitLabel(Territories.Siberia, 882, 136);
        addUnitLabel(Territories.Mongolia, 849, 249);
        addUnitLabel(Territories.Kazakhstan, 819, 212);
        addUnitLabel(Territories.Japan, 940, 261);
        addUnitLabel(Territories.China, 783, 285);
        addUnitLabel(Territories.NorthCorea, 853, 294);
        addUnitLabel(Territories.SouthCorea, 886, 318);
        addUnitLabel(Territories.Thailand, 889, 363);
        addUnitLabel(Territories.Bangladesh, 841, 361);
        addUnitLabel(Territories.India, 786, 370);
        addUnitLabel(Territories.Pakistan, 732, 313);
        addUnitLabel(Territories.Iran, 703, 332);
        addUnitLabel(Territories.Iraq, 663, 331);
        addUnitLabel(Territories.Turkey, 708, 241);
        addUnitLabel(Territories.Siria, 659, 279);
        addUnitLabel(Territories.SaudiArabia, 664, 401);
        addUnitLabel(Territories.Jordan, 612, 338);
    }

    private void addUnitLabel(Territories territory, int x, int y) {
        UnitsLabel label = new UnitsLabel(territory, controller);
        controller.addTerritoryObserver(territory, label);
        label.setLocation(x, y);
        panel.add(label);
    }

    private void didPressNextRound() {
        System.out.println("Nova rodada");
        controller.didSelectNewRound();
    }

    private void didPressThrowDice() {
        System.out.println("Jogar dados");
    }

    private void didPressSeeObjective() {
        System.out.println("Ver Objetivo");
        controller.didSelectShowObjective();
    }

    private void didPressChooseDicesValue() {
        System.out.println("Escolher valor dos dados");
        controller.didSelectChooseDicesValue();
    }

    public void showPlayerOrder(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void showPlayerObjective(String objective) {
        ObjectiveCardView.showObjectiveFrame(objective);
    }

    public void showChooseDicesView() {
        ChooseDicesView.showFrame();
    }

    public static void main(String[] args) {
        MapController mapController = new MapController(new PlayerInfo[] {
            new PlayerInfo("Alexandre", PlayerColor.blue),
            new PlayerInfo("Fernanda", PlayerColor.red),
            new PlayerInfo("Marcia", PlayerColor.white)
        });
        MapView mapView = new MapView(mapController);
        mapView.setVisible(true);
    }
}

class MapPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage mapImage;
    private BufferedImage backgroundImage;

    MapPanel() {
        try {
            mapImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_mapa copy.png"));
            backgroundImage = ImageIO.read(getClass().getResource("/images/war_tabuleiro_fundo.png"));
        } catch(IOException e) {
            System.out.print("Erro ao carregar imagem" + e.getMessage());
        }
        setLayout(null);
        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(String.format("x: %d, y: %d", e.getX(), e.getY()));
            }
        });
    }

    @Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(mapImage, 0, 0, null);
	}
}