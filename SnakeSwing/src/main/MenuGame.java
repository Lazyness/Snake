package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final public class MenuGame extends JPanel{
	
    public MenuGame() {
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
    }
    
    public void paintScore(Graphics g) {
        g.setColor(Color.orange);
        g.setFont(new Font("Digital-7",14,20));
        g.drawString("Score: " + GameField.getVarScore(),40,110);
    }
    
    public void paintResult(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Digital-7",14,20));
        g.drawString("Result Game: ", 30,80);
    }
    
    public void paintTitle(Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(new Font("Digital-7",14,40));
        g.drawString("Snake", 30,40);
    }

    public void paintTimeGame(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Digital-7",14,20));
        g.drawString("Time Game: ", 30, 150);
        
        g.setColor(Color.orange);
        g.setFont(new Font("Digital-7",14,20));
        g.drawString("h:"+GameField.getHours()+" m:"+GameField.getMinutes()+" s:"+GameField.getSeconds(),40,180);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTitle(g);
        paintResult(g);
        paintScore(g);
        paintTimeGame(g);
        repaint();
    }

}

