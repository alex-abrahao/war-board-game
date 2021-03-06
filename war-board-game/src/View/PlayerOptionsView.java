package View;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

import Model.PlayerColor;
import Model.PlayerInfo;

public class PlayerOptionsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 385;
    private static final int DEFAULT_HEIGHT = 300;

    private PlayerOptionsDelegate delegate;
    private JButton doneButton;
    JTextField[] nameTextField = new JTextField[6];
    PlayerInfo[] players;
    final PlayerColor[] playerColors = new PlayerColor[]{
        PlayerColor.red, 
        PlayerColor.green, 
        PlayerColor.blue, 
        PlayerColor.yellow,
        PlayerColor.black,
        PlayerColor.white
    };

    public PlayerOptionsView(PlayerOptionsDelegate delegate) {
        this.delegate = delegate;

        Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int x = screenSize.width/2 - DEFAULT_WIDTH/2;
        int y = screenSize.height/2 - DEFAULT_HEIGHT/2;
        
        setBounds(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT + 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        setTitle();
        createTextField();
        setButton();
    }

    private void createTextField() {
        for (int i = 0; i < 6; i++) {
            colorsLabel(i);
            nameTextField[i] = new JTextField();
            final int yPosition = 40 + 30 * i;
            nameTextField[i].setBounds(120, yPosition, 220, 30);
            add(nameTextField[i]);
        }
    }

    private void setButton(){
        doneButton = new JButton("Pronto");
        doneButton.setBounds(20, 220, 120, 40);
        add(doneButton);

        doneButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                checkPlayers();
            } 
        });
    }

    private void checkPlayers(){
        int numberOfPlayers = 0;
        boolean playersName = false;
        for1: for (int i = 0; i < 6; i++) {
            playersName = false;
            numberOfPlayers += checkNumbersOfPlayers(i);
            for (int j = i+1; j<5; j++){
                if(checkPlayersName(i, j)){
                    JOptionPane.showMessageDialog(doneButton, "Ops, tem nome de jogadores iguais :(");
                    break for1;
                }
            }
            playersName = true;
        }
        if (numberOfPlayers < 3 && playersName){
            JOptionPane.showMessageDialog(doneButton, "Numero de jogadores invalido");
        } else if (playersName){
            startGame(numberOfPlayers);
            System.out.println("Tudo certo, pode comecar");
        }
    }

    private void startGame(int numberOfPlayers){
        players = new PlayerInfo[numberOfPlayers];
        int number = 0;
        for(int i=0; i<6; i++){
            if(!nameTextField[i].getText().equals("")){
                players[number] = new PlayerInfo(nameTextField[i].getText(), playerColors[i]);
                System.out.println(String.format("Nome: %s, cor: %s", players[number].name, players[number].color.getName()));
                number++;
            }
        }
        delegate.didSetPlayers(players);
        dispose();
    }

    private int checkNumbersOfPlayers(int index){
        if(!nameTextField[index].getText().equals("")){
            return 1;
        }
        return 0;
    }

    private boolean checkPlayersName(int i, int j){
        if(nameTextField[i].getText().equals(nameTextField[j].getText()) && !nameTextField[i].getText().equals("")){
            return true;
        }
        return false;
    }

    private void setTitle(){
        JLabel title = new JLabel("Digite o nome dos jogadores:");
        title.setBounds(20, 8, 220, 40);
        add(title);
    }

    private void colorsLabel(int index){
        JLabel color = new JLabel(playerColors[index].getName());
        color.setBounds(20, 40 + 30 * index, 100, 30);
        add(color);
    }

    public static void main(String[] args) {
        PlayerOptionsView box = new PlayerOptionsView(null);
        box.setVisible(true);
    }
}