package proz.game;

import javax.swing.JFrame;

import proz.game.controller.Controller;
import proz.game.model.Player;
import proz.game.view.SwingView;

public class Main {
    private static Player createModel(){
        return new Player();
    }

    private static Controller createController(Player p){
        return new Controller(p);
    }

    private static SwingView createModelViewController(){
        Player p = createModel();
        Controller c = createController(p);
        SwingView v = new SwingView();
        v.setModel(p);
        v.setController(c);
        c.setView(v);
        return v;
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Space shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingView v = createModelViewController();
        frame.getContentPane().add(v);

        frame.setSize(v.getSize());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        v.requestFocus();
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
