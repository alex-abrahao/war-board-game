package View;

import javax.swing.*;

public class NumberOfPlayersView extends JFrame {

    private static final long serialVersionUID = 5453506841656979450L;
    private static final int DEFAULT_WIDTH = 385;
    private static final int DEFAULT_HEIGHT = 300;

    private ButtonGroup numbersGroup = new ButtonGroup();
    private JButton doneButton = new JButton("OK");

    NumberOfPlayersView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        getContentPane().setLayout(null);

        createCheckBox();
    }

    private void createCheckBox() {
        JLabel title = new JLabel("Escolha o número de jogadores");
        title.setBounds(20, 8, 220, 40);

        for (int numberOfPlayers = 3; numberOfPlayers <= 6; numberOfPlayers++) {
            JRadioButton numberRadioButton = newRadioButton(numberOfPlayers);
            add(numberRadioButton);
            numbersGroup.add(numberRadioButton);
        }

        doneButton.setBounds(20, 170, 120, 40);
        // doneButton.addActionListener((ActionListener) this);

        add(title);
        add(doneButton);
    }

    private JRadioButton newRadioButton(int number) {
        final JRadioButton button = new JRadioButton(String.valueOf(number));
        final int yPosition = 35 + (30 * (number - 3));
        button.setBounds(20, yPosition, 120, 40);
        return button;
    }

    public static void main(String[] args) {
        NumberOfPlayersView box = new NumberOfPlayersView();
        box.setVisible(true);
    }
}