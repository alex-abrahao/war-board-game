package Controller;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;

import View.MapView;
import View.NewGameView;

public class NewGameController {

    private NewGameView view;

    public NewGameController() {
    }

    public void setView(NewGameView view) {
        this.view = view;
    }

    public void didSelectNewGame() {
        JFrame frame = new JFrame();
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int x = screenSize.width / 2 - MapView.DEFAULT_WIDTH / 2;
        int y = screenSize.height / 2 - MapView.DEFAULT_HEIGHT / 2;
        frame.setBounds(x, y, MapView.DEFAULT_WIDTH, MapView.DEFAULT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new MapView());
        frame.setVisible(true);
        frame.setTitle("WAR");
        view.dispose();
    }
}
