package main;

import javax.swing.*;
import javax.xml.crypto.Data;

import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class Snake extends JFrame {
	private GameField gameField = null;
	private MenuGame menuGame = null;
    public Snake(){
    	setUndecorated(true);
    	setResizable(false);
    	getContentPane().setBackground(Color.BLACK);
    	gameField = new GameField();
    	menuGame = new MenuGame();
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(575,365);
        setLocation(600,200);
        getContentPane().setLayout(new MigLayout("", "[462.00px][280px]", "[340px,grow]"));
        getContentPane().add(gameField, "cell 0 0,grow");
        getContentPane().add(menuGame, "cell 1 0,grow");

        setVisible(true);
    }

    public static void main(String[] args) {
        Snake snake = new Snake();
    }
}
