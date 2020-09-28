package minesweeper.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author godghdai
 * #Description Sprite
 * #Date: 2020/9/29 04:04
 */
public class Sprite {
    public final static int Mask = 0;
    public final static int Mine = 1;
    public final static int Flag = 2;
    public final static int Blank = 3;
    public final static int Number = 4;

    private static Image image = new ImageIcon(ClassLoader.getSystemResource("mine.png")).getImage();
    private static int size = 32;

    public static void drawImage(Graphics g, int x, int y, int index) {
        g.drawImage(image,
                x * size, y * size, (x + 1) * size, (y + 1) * size,
                index * size, 0, (index + 1) * size, size,
                null);
    }
}
