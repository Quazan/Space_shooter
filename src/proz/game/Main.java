package proz.game;

import javax.swing.*;

import proz.game.controller.Controller;
import proz.game.model.Board;
import proz.game.view.SwingView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
        //trzeba tutaj posprzątać
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

            if("EXIT".equals(e.getActionCommand())){
                System.exit(0);
            }
        };

        menuItem.setActionCommand("NEW");
        menuItem.addActionListener(menuListener);

        menuItem = new JMenuItem("Leaderboards");
        menu.add(menuItem);

        menuItem.setActionCommand("Scores");
        menuItem.addActionListener(menuListener);

        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);

        menuItem.setActionCommand("EXIT");
        menuItem.addActionListener(menuListener);

        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
