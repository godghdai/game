package com.game.minesweeper.gui;

import com.game.minesweeper.GameControl;

import javax.swing.*;

/**
 * @author godghdai
 * #Description JMainMenuBar
 * #Date: 2020/9/28 14:13
 */
public class JMainMenuBar extends JMenuBar {
    private GameControl control = null;

    public JMainMenuBar() {
        super();
        JMenu file = new JMenu("文件");
        JMenuItem setting = new JMenuItem("设置");
        setting.addActionListener(e -> {
            if (control != null)
                control.onClickedSetting();
        });

        JMenuItem exit = new JMenuItem("退出");
        exit.addActionListener(e -> System.exit(0));
        file.add(setting);
        file.addSeparator();
        file.add(exit);
        add(file);

        JMenu help = new JMenu("帮助");
        JMenuItem about = new JMenuItem("关于");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "godghdai@gmail.com"));
        help.add(about);
        add(help);
    }

    public void setControl(GameControl control) {
        this.control = control;
    }

}
