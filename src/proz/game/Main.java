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

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Space shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
/*
        SwingView v = createModelViewController();
        frame.getContentPane().add(v);

        frame.setSize(v.getSize());

 */
        showMenu(frame);

        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        //v.requestFocus();
    }

    private static void startGame(JFrame frame){
        SwingView v = createModelViewController();
        frame.getContentPane().add(v);

        //frame.setSize(v.getSize());

        v.requestFocus();
    }

    private static void showMenu(JFrame frame){
        JPanel p = new JPanel();

        JButton startGame = new JButton("START GAME");
        startGame.setActionCommand("START");
        JButton exit = new JButton("EXIT");
        exit.setActionCommand("EXIT");

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("START".equals(e.getActionCommand())){
                    //frame.remove(startGame);
                    //frame.remove(exit);
                    frame.remove(p);
                    startGame(frame);
                    //startGame.setEnabled(false);
                    //startGame.setVisible(false);
                    //exit.setEnabled(false);
                    //exit.setVisible(false);
                }
                if("EXIT".equals(e.getActionCommand())){
                    System.exit(0);
                }
            }
        };

        startGame.addActionListener(actionListener);
        exit.addActionListener(actionListener);

        p.setLayout(null);
        startGame.setBounds(200, 200, 100, 100);
        p.add(startGame);
        exit.setBounds(200, 400, 100, 100);
        p.add(exit);

        frame.add(p);

        //frame.getContentPane().setBackground(Color.black);
        //frame.getContentPane().add(BorderLayout.NORTH,startGame);
        //frame.getContentPane().add(BorderLayout.SOUTH, exit);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
