package View;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class PlayerOptionsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 385;
    private static final int DEFAULT_HEIGHT = 300;

    private JButton doneButton;
    JTextField[] nameTextField = new JTextField[6];

    public PlayerOptionsView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        getContentPane().setLayout(null);

        setTitle();
        createTextField();
        setButton();
    }

    private void createTextField() {
        for (int i = 0; i < 6; i++) {
            nameTextField[i] = new JTextField("nome do jogador " + (i+1));
            final int yPosition = 40 + 30 * i;
            nameTextField[i].setBounds(20, yPosition, 220, 30);
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
            JOptionPane.showMessageDialog(doneButton, "Número de jogadores inválido");
        } else if (playersName){
            System.out.println("Tudo certo, pode começar");
        }
    }

    private int checkNumbersOfPlayers(int index){
        if((!nameTextField[index].getText().equals("nome do jogador " + (index+1))) && !nameTextField[index].getText().equals("")){
            return 1;
        }
        return 0;
    }

    private boolean checkPlayersName(int i, int j){
        if(nameTextField[i].getText().equals(nameTextField[j].getText())){
            return true;
        }
        return false;
    }
    private void setTitle(){
        JLabel title = new JLabel("Digite o nome dos jogadores:");
        title.setBounds(20, 8, 220, 40);
        add(title);
    }

    public static void main(String[] args) {
        PlayerOptionsView box = new PlayerOptionsView();
        box.setVisible(true);
    }
}