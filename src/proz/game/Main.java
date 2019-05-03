package proz.game;

import proz.game.controller.Controller;
import proz.game.model.Board;
import proz.game.view.SwingView;
import proz.game.model.GameMenu;

import javax.swing.*;
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
            if("Scores".equals(e.getActionCommand())){
                showLeaderboards();
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

    private static void createMainMenu(JFrame frame){
        GameMenu mainMenu = new GameMenu();
        mainMenu.setLayout(null);

        //ImageIcon ii;

        JButton newGameButton = new JButton("New game");
        //ii = new ImageIcon("C:\\Users\\alexe\\OneDrive - Politechnika Warszawska\\Pulpit\\Space_shooter\\assets\\PNG\\UI\\buttonRed.png");
        //newGameButton.setIcon(ii);
        newGameButton.setActionCommand("START");
        newGameButton.setBounds(frame.getWidth()/2-150, 200,300 , 50);
        mainMenu.add(newGameButton);

        JButton leaderboardsButton = new JButton("Leaderboards");
        leaderboardsButton.setActionCommand("SCORES");
        leaderboardsButton.setBounds(frame.getWidth()/2-150, 300, 300, 50);
        mainMenu.add(leaderboardsButton);

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
                if("SCORES".equals(e.getActionCommand())){
                    showLeaderboards();
                }
                if("EXIT".equals(e.getActionCommand())){
                    System.exit(0);
                }
            }
        };

        newGameButton.addActionListener(mainMenuListener);
        leaderboardsButton.addActionListener(mainMenuListener);
        exitButton.addActionListener(mainMenuListener);
        frame.add(mainMenu);
    }

    private static void showLeaderboards() {

        //tymczasowo -> trzeba ogarnąć JSONA do tego

        JFrame frame = new JFrame("Leaderboards");
        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

        JTable scoresTable = new JTable(data, columnNames);

        frame.setSize(800, 600);

        frame.getContentPane().add(scoresTable);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
