package View;

import javax.swing.*;

public class PlayerOptionsView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 385;
    private static final int DEFAULT_HEIGHT = 300;

    private JButton doneButton;
    private ButtonGroup colorGroup = new ButtonGroup();

    public PlayerOptionsView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        getContentPane().setLayout(null);

        createTextField();
    }

    private void createTextField() {
        
        JTextField nameTextField = new JTextField("nome do jogador");
        nameTextField.setBounds(20, 40, 220, 30);
        JLabel title = new JLabel("Digite o nome do jogador:");
        title.setBounds(20, 8, 220, 40);

        doneButton = new JButton("Pronto");
        doneButton.setBounds(20, 190, 120, 40);

        add(title);
        add(nameTextField);
        add(doneButton);
        colorOptions(new String[] {
            "vermelho",
            "verde",
            "azul",
            "amarelo",
            "preto",
            "branco"
        });
    }

    private void colorOptions(String[] colorNames) {
        JLabel title = new JLabel("Escolha a cor do jogador:");
        title.setBounds(20, 60, 220, 30);
        add(title);

        for (int i = 0; i < colorNames.length; i++) {
            final int xPosition = (i % 2 == 0) ? 20 : 120;
            final int yPosition = 80 + 30 * ( (i % 2 == 0) ? i/2 : (i-1)/2 );
            JRadioButton colorButton = newColorRadioButton(colorNames[i], xPosition, yPosition);
            add(colorButton);
            colorGroup.add(colorButton);
        }
    }

    private JRadioButton newColorRadioButton(String colorName, int x, int y) {
        JRadioButton colorButton = new JRadioButton(colorName);
        colorButton.setBounds(x, y, 90, 30);
        return colorButton;
    }

    public static void main(String[] args) {
        PlayerOptionsView box = new PlayerOptionsView();
        box.setVisible(true);
    }
}