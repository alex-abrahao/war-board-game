package Controller;

import Model.PlayerInfo;

import View.MapView;
import View.NewGameView;
import View.PlayerOptionsDelegate;
import View.PlayerOptionsView;

public class NewGameController implements Controller<NewGameView>, PlayerOptionsDelegate {

    private NewGameView view;

    @Override
    public void setView(NewGameView view) {
        this.view = view;
    }

    public void didSelectNewGame() {
        PlayerOptionsView optionsView = new PlayerOptionsView(this);
        optionsView.setVisible(true);
        // JFrame frame = new JFrame();
        // Toolkit tk = Toolkit.getDefaultToolkit();
        // Dimension screenSize = tk.getScreenSize();
        // int x = screenSize.width / 2 - MapView.DEFAULT_WIDTH / 2;
        // int y = screenSize.height / 2 - MapView.DEFAULT_HEIGHT / 2;
        // frame.setBounds(x, y, MapView.DEFAULT_WIDTH, MapView.DEFAULT_HEIGHT);
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // frame.setResizable(false);
        // frame.getContentPane().add(new MapView());
        // frame.setVisible(true);
        // frame.setTitle("WAR");
    }

    private void startNewGame(PlayerInfo[] players) {
        // TODO: Criar novo controller do MapView
        view.dispose();
    }

    @Override
    public void didSetPlayers(PlayerInfo[] players) {
        startNewGame(players);
    }
}
