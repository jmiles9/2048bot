package learning_notinactualproject;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LearnKeys implements KeyListener {

    public LearnKeys(){
        JFrame f = new JFrame();
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(800, 1000);
        f.addKeyListener(this);
    }
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();

        System.out.println(keyCode);
    }

    public void keyReleased(KeyEvent e){
        //dont care about what happens here
    }

    public void keyTyped(KeyEvent e){
        //dont care about this either
    }

    public static void main(String [] args){
        LearnKeys butt = new LearnKeys();
    }

}

