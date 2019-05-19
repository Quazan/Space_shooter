package proz.game;

import proz.game.controller.Controller;
import proz.game.model.Board;
import proz.game.model.GameMenu;
import proz.game.view.SwingView;

import javax.swing.*;
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
        //trzeba tutaj posprzątać
        JFrame frame = new JFrame("Space shooter");
        frame.setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New game");
        menu.add(menuItem);

        createMainMenu(frame);

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

    private static void createMainMenu(JFrame frame){
        GameMenu mainMenu = new GameMenu();
        mainMenu.setLayout(null);

        JButton newGameButton = new JButton("New game");

        newGameButton.setActionCommand("START");
        newGameButton.setBounds(frame.getWidth()/2-150, 300,300 , 50);
        mainMenu.add(newGameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.setBounds(frame.getWidth()/2-150, 400, 300, 50);
        mainMenu.add(exitButton);

        ActionListener mainMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("START".equals(e.getActionCommand())){
                    startGame(frame);
                }
                if("EXIT".equals(e.getActionCommand())){
                    System.exit(0);
                }
            }
        };

        newGameButton.addActionListener(mainMenuListener);
        exitButton.addActionListener(mainMenuListener);
        frame.add(mainMenu);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
