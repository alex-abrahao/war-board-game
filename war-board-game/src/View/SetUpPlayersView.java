package View;

import javax.swing.*;

public class SetUpPlayersView extends JFrame {

    private static final long serialVersionUID = 1L;

    JRadioButton threePlayers, fourPlayers, fivePlayers, sixPlayers;
    JButton button;
    //JRadioButton color1, color2;

    public SetUpPlayersView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(385, 300);
        setResizable(false);
        getContentPane().setLayout(null);

        //createCheckBox();
        createTextField();
    }

    private void createCheckBox() {
        ButtonGroup group = new ButtonGroup();
        JLabel title = new JLabel("Escolha o numero de jogadores");
        title.setBounds(20, 8, 220, 40);

        threePlayers = new JRadioButton("3");
        threePlayers.setBounds(20, 30, 120, 40);

        fourPlayers = new JRadioButton("4");
        fourPlayers.setBounds(20, 60, 120, 40);

        fivePlayers = new JRadioButton("5");
        fivePlayers.setBounds(20, 90, 120, 40);

        sixPlayers = new JRadioButton("6");
        sixPlayers.setBounds(20, 120, 120, 40);

        JButton button = new JButton("OK");
        button.setBounds(20, 170, 120, 40);
        // button.addActionListener((ActionListener) this);

        add(title);
        add(threePlayers);
        add(fourPlayers);
        add(fivePlayers);
        add(sixPlayers);
        group.add(threePlayers);
        group.add(fourPlayers);
        group.add(fivePlayers);
        group.add(sixPlayers);

        add(button);
    }

    private void createTextField(){
        
        JTextField nameTextField = new JTextField("nome do jogador");
        nameTextField.setBounds(20, 40, 220, 30);
        JLabel title = new JLabel("Digite o nome do jogador:");
        title.setBounds(20, 8, 220, 40);

        JButton button = new JButton("Pronto");
        button.setBounds(20, 190, 120, 40);

        add(title);
        add(nameTextField);
        add(button);
        colorOptions();
    }

    private void colorOptions(){
        ButtonGroup colorGroup = new ButtonGroup();
        JLabel title = new JLabel("Escolha a cor do jogador");
        title.setBounds(20, 60, 220, 30);

        JRadioButton color1 = new JRadioButton("vermelho");
        color1.setBounds(20, 80, 90, 30);

        JRadioButton color2 = new JRadioButton("verde");
        color2.setBounds(120, 80, 90, 30);

        JRadioButton color3 = new JRadioButton("azul");
        color3.setBounds(20, 110, 90, 30);

        JRadioButton color4 = new JRadioButton("amarelo");
        color4.setBounds(120, 110, 90, 30);

        JRadioButton color5 = new JRadioButton("preto");
        color5.setBounds(20, 140, 90, 30);

        JRadioButton color6 = new JRadioButton("branco");
        color6.setBounds(120, 140, 90, 30);
        
        add(title);
        add(color1);
        add(color2);
        add(color3);
        add(color4);
        add(color5);
        add(color6);
        colorGroup.add(color1);
        colorGroup.add(color2);
        colorGroup.add(color3);
        colorGroup.add(color4);
        colorGroup.add(color5);
        colorGroup.add(color6);
    }

    // public void actionPerformed(Action e) {
    //     if (e.getSource() == button){
    //         getPlayersNumber();
    //     }
    // }
    
    // private void getPlayersNumber() {
        //int playersNumber;
        // if (threePlayers.isSelected()){
        //     playersNumber = 3;
        // }
        // else if (fourPlayers.isSelected()){
        //     playersNumber = 4;
        // }
        // else if (fourPlayers.isSelected()){
        //     playersNumber = 5;
        // }
        // else if (fivePlayers.isSelected()){
        //     playersNumber = 6;
        // }

       // JOptionPane.showMessageDialog(null, "pronto");
    //}

    public static void main(String[] args) {
        SetUpPlayersView box = new SetUpPlayersView();
        box.setVisible(true);
    }
}