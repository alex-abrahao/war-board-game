package Controller;

import javax.swing.JFrame;

public interface Controller<View extends JFrame> {
    public void setView(View view);
}