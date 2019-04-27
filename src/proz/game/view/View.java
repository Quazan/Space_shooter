package proz.game.view;

import proz.game.controller.Controller;
import proz.game.model.Player;

public interface View {
    void updateView();

    void setModel(Player player);
    void setController(Controller c);

    int getWidth();
    int getHeight();
}
