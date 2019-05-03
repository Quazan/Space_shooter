package proz.game.model;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    @Override
    protected void paintComponent(Graphics g1){
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        fillBackground(g);
    }

    private void fillBackground(Graphics2D g){
        ImageIcon ii = new ImageIcon("assets\\Backgrounds\\darkPurple.png");
        Image imageBackground = ii.getImage();
        int h = imageBackground.getHeight(null);
        int w = imageBackground.getWidth(null);
        for (int y = 0; y <= getHeight() + 10; y += h)
            for (int x = 0; x <= getWidth() + 10; x += w){
                g.drawImage(imageBackground, x, y, this);
            }

        ii = new ImageIcon("C:\\Users\\alexe\\OneDrive - Politechnika Warszawska\\Pulpit\\Space_shooter\\assets\\PNG\\logo.png");
            Image logo = ii.getImage();
            g.drawImage(logo, getWidth()/2-ii.getIconWidth()/2, 30, null);
    }
}
