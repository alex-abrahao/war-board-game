package View;

import javax.swing.*;

public class PlayerOptionsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 385;
    private static final int DEFAULT_HEIGHT = 300;

    private JButton doneButton;

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
        JTextField[] nameTextField = new JTextField[6];
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