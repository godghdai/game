package com.game.minesweeper.gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author godghdai
 * #Description JNumberField
 * #Date: 2020/9/28 04:31
 */
public class JNumberField extends JTextField {
    private String labelName;
    public JNumberField(String labelName){
        super();
        this.labelName=labelName;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar=e.getKeyChar();
                if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
                    e.consume();
                }
            }
            }
        );
    }

    public String getLabelName() {
        return labelName;
    }

    public int getValue(){
        String str=getText();
        if(str.equals("")) return 0;
        return Integer.parseInt(str);
    }
}
