package View;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

import Model.Match;

public class ChooseDicesView extends JFrame{

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    private JButton doneButton;
    JTextField[] attackDicesTextField = new JTextField[3];
    JTextField[] defenceDicesTextField = new JTextField[3];
    Integer[] attackDices = new Integer[3]; 
    Integer[] defenseDices = new Integer[3];


    public ChooseDicesView(){

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
        for (int i = 0; i < 3; i++) {
            attackDicesTextField[i] = new JTextField("0");
            defenceDicesTextField[i] = new JTextField("0");
            final int yPosition = 40 + 30 * i;
            attackDicesTextField[i].setBounds(20, yPosition, 50, 30);
            defenceDicesTextField[i].setBounds(110, yPosition,50,30);
            add(attackDicesTextField[i]);
            add(defenceDicesTextField[i]);
        }
    }

    private void setButton(){
        doneButton = new JButton("Pronto");
        doneButton.setBounds(50, 150, 100, 40);
        add(doneButton);

        doneButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                getDices();
                dispose();
            } 
        });
    }

    private void setTitle(){
        JLabel attackLabel = new JLabel("Ataque:");
        attackLabel.setBounds(20, 8, 100, 40);
        add(attackLabel);

        JLabel defenceLabel = new JLabel("Defesa:");
        defenceLabel.setBounds(110, 8, 100, 40);
        add(defenceLabel);
    }
    
    private void getDices() {
        for(int i = 0; i < 3; i++){
            if(!attackDicesTextField[i].getText().equals("")){
                attackDices[i] = Integer.parseInt(attackDicesTextField[i].getText());
             
            }   
            if(!defenceDicesTextField[i].getText().equals("")){
                defenseDices[i] = Integer.parseInt(defenceDicesTextField[i].getText());
            }
        }
        Match.getInstance().setCheatDice(attackDices, defenseDices);
    }

    public static void showFrame(){
        ChooseDicesView box = new ChooseDicesView();
        box.setVisible(true);
    }

    public static void main(String[] args) {
        ChooseDicesView box = new ChooseDicesView();
        box.setVisible(true);
    }
}