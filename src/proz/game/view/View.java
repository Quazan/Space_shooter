package proz.game.view;

import proz.game.controller.Controller;
import proz.game.model.Board;

public interface View {
    void updateView();

    void setModel(Board board);
    void setController(Controller c);

    int getWidth();
    int getHeight();
}
