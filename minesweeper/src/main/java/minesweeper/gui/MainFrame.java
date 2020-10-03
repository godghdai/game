package minesweeper.gui;

import minesweeper.core.DataStore;
import minesweeper.core.GameControl;
import minesweeper.common.DataChangeListener;
import minesweeper.util.ClassUtil;
import minesweeper.util.Copy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * @author godghdai
 * #Description MineFrame
 * #Date: 2020/9/27 14:05
 */

public class MainFrame extends JFrame implements DataChangeListener {
    private static Image icon = new ImageIcon(ClassLoader.getSystemResource("app.png")).getImage();
    private MinePanel canvasPanel;
    private GameControl control;
    private DataStore dataStore;
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

    private int width;
    private int height;
    private JLabel jLabelMineCount;
    private JLabel jLabelMineRemain;
    private BufferedImage bufferedImage;

    public MainFrame(String title, DataStore dataStore, GameControl control) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ClassUtil.copyProperty(this, dataStore);
        canvasPanel = new MinePanel();
        this.control = control;
        this.dataStore = dataStore;
        this.width = maxX * size;
        this.height = maxY * size;
        initGui();
        setIconImage(icon);
    }

    private void initGui() {
        JMainMenuBar menuBar = new JMainMenuBar();
        menuBar.setControl(control);
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        canvasPanel.setPreferredSize(new Dimension(width, height));
        add(canvasPanel, "Center");
        initNorthPanel();
        //setResizable(false);
        pack();
    }

    private void initNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(width, 24));
        northPanel.setLayout(new GridLayout(1, 4, 0, 0));
        jLabelMineCount = new JLabel("总雷数:" + dataStore.mineCount);
        northPanel.add(jLabelMineCount);
        jLabelMineRemain = new JLabel("剩余数:" + dataStore.mineCount);
        northPanel.add(jLabelMineRemain);
        add(northPanel, "South");
    }

    public void showCenter() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void dataChanged(DataStore source) {
        ClassUtil.copyProperty(this, source);
        jLabelMineCount.setText("雷数:" + source.mineCount);
        jLabelMineRemain.setText("剩余数:" + source.mineCount);
        width = maxX * size;
        height = maxY * size;
        remove(canvasPanel);
        canvasPanel = new MinePanel();
        canvasPanel.setPreferredSize(new Dimension(width,height));
        add(canvasPanel, "Center");
        pack();
        setLocationRelativeTo(null);

        bufferedImage = null;
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

        void drawBackground(Graphics g) {
            if (bufferedImage != null) {
                g.drawImage(bufferedImage, 0, 0, width, height, null);
                return;
            }
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics graphics = bufferedImage.getGraphics();

            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (data[y][x] == -1) {
                        Sprite.drawImage(graphics, x, y, Sprite.Mine);
                    } else if (data[y][x] > 0) {
                        Sprite.drawImage(graphics, x, y, Sprite.Number + data[y][x] - 1);
                    } else if (data[y][x] == 0) {
                        Sprite.drawImage(graphics, x, y, Sprite.Blank);
                    }
                }
            }
        }

        void drawMaskFlag(Graphics g) {
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    if (!dataOpen[y][x]) {
                        Sprite.drawImage(g, x, y, Sprite.Mask);
                    }
                    if (dataFlag[y][x]) {
                        Sprite.drawImage(g, x, y, Sprite.Flag);
                    }
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBackground(g);
            drawMaskFlag(g);
        }

    }

    public void setRemain(int count) {
        jLabelMineRemain.setText("剩余数:" + count);
    }

}

