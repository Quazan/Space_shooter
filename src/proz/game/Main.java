package proz.game;

import javax.swing.*;

import proz.game.controller.Controller;
import proz.game.model.Board;
import proz.game.view.SwingView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Board createModel(){
        return new Board();
    }

    private static Controller createController(Board b){
        return new Controller(b);
    }

    private static SwingView createModelViewController(){
        Board b = createModel();
        Controller c = createController(b);
        SwingView v = new SwingView();
        v.setModel(b);
        v.setController(c);
        c.setView(v);
        return v;
    }

    private static void startGame(JFrame frame){
        frame.getContentPane().removeAll();
        SwingView v = createModelViewController();
        frame.getContentPane().add(v);
        v.requestFocus();
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Space shooter");
        frame.setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New game");
        menu.add(menuItem);

        ActionListener menuListener = e -> {
            if("NEW".equals(e.getActionCommand())){
                startGame(frame);
            }
        };

        menuItem.setActionCommand("NEW");
        menuItem.addActionListener(menuListener);
        frame.setJMenuBar(menuBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //SwingView v = createModelViewController();
        //frame.getContentPane().add(v);

        //frame.setSize(v.getSize());


        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        //v.requestFocus();
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
