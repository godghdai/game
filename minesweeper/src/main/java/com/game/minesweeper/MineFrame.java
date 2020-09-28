package com.game.minesweeper;

import com.game.minesweeper.common.DataChangeListener;
import com.game.minesweeper.gui.JMainMenuBar;
import com.game.minesweeper.util.ClassUtil;
import com.game.minesweeper.util.Copy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author godghdai
 * #Description MineFrame
 * #Date: 2020/9/27 14:05
 */

public class MineFrame extends JFrame implements DataChangeListener{
    private MinePanel canvasPanel;
    private GameControl control;
    private DataCenter dataCenter;
    @Copy
    private int[][] data = null;
    @Copy
    private boolean[][] dataOpen = null;
    @Copy
    private boolean[][] dataFlag = null;
    @Copy
    private int size;
    @Copy
    private int maxX;
    @Copy
    private int maxY;

    private static Image image = new ImageIcon(ClassLoader.getSystemResource("扫雷.png")).getImage();
    private static Image icon = new ImageIcon(ClassLoader.getSystemResource("app.png")).getImage();
    private JLabel jLabelMineCount;
    private JLabel jLabelMineRemain;
    public MineFrame(String title, int width, int height, DataCenter dataCenter, GameControl control) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ClassUtil.copyProperty(this, dataCenter);
        canvasPanel = new MinePanel();
        this.control = control;
        this.dataCenter = dataCenter;
        initGui(width, height);
        setIconImage(icon);
    }

    public void initGui(int width, int height) {
        JMainMenuBar menuBar = new JMainMenuBar();
        menuBar.setControl(control);
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        canvasPanel.setPreferredSize(new Dimension(width, height));
        add(canvasPanel, "Center");

        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(width, 24));
        northPanel.setLayout(new GridLayout(1, 4, 0, 0));
        jLabelMineCount = new JLabel("总雷数:" + dataCenter.mineCount);
        northPanel.add(jLabelMineCount);
        jLabelMineRemain=new JLabel("剩余数:"+ dataCenter.mineCount);
        northPanel.add(jLabelMineRemain);
        add(northPanel, "South");
        //setResizable(false);
        pack();
    }

    public void setRemain(int count){
        jLabelMineRemain.setText("剩余数:"+count);
    }

    public void showCenter() {

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void dataChanged(DataCenter source) {
        System.out.println("MineFrame dataChanged");
        ClassUtil.copyProperty(this, source);
        jLabelMineCount.setText("雷数:" + source.mineCount);
        jLabelMineRemain.setText("剩余数:"+ source.mineCount);
        canvasPanel.setPreferredSize(new Dimension(maxX * size, maxY * size));
        pack();
        setLocationRelativeTo(null);
    }

    private class MinePanel extends JPanel {
        MinePanel() {
            //setFocusable(true);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    control.onMouseReleased(e);
                }
            });
        }

        void drawBg(Graphics g) {
            int offsetX;
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    int dx1 = x * size;
                    int dy1 = y * size;
                    int dx2 = dx1 + size;
                    int dy2 = dy1 + size;
                    if (data[y][x] == -1) {
                        g.drawImage(image, dx1, dy1, dx2, dy2, 32, 0, 64, 32, null);
                    } else if (data[y][x] > 0) {
                        offsetX = data[y][x] * 32 + 96;
                        g.drawImage(image, dx1, dy1, dx2, dy2, offsetX, 0, offsetX + 32, 32, null);
                    } else if (data[y][x] == 0) {
                        offsetX = 3 * 32;
                        g.drawImage(image, dx1, dy1, dx2, dy2, offsetX, 0, offsetX + 32, 32, null);
                    }
                }
            }
        }

        void drawMask(Graphics g) {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (!dataOpen[y][x]) {
                        g.drawImage(image, x * 32, y * 32, x * 32 + 32, y * 32 + 32, 0, 0, 32, 32, null);
                    }

                }
            }
        }

        public void drawFlag(Graphics g) {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (dataFlag[y][x]) {
                        g.drawImage(image, x * 32, y * 32, x * 32 + 32, y * 32 + 32, 64, 0, 96, 32, null);
                    }

                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBg(g);
            drawMask(g);
            drawFlag(g);
        }

    }

}

