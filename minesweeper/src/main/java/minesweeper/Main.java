package minesweeper;

import minesweeper.core.*;
import minesweeper.gui.MainFrame;

import javax.swing.*;
import java.io.File;

public class Main {
    public static BaseConfig getBaseConfig() {
        String path = System.getProperty("user.dir") + File.separator + "config.properties";
        ConfigLoader configLoader = new ConfigLoader();
        boolean load = configLoader.load(path);
        if (!load) {
            JOptionPane.showMessageDialog(null, path + ":路径不存在！！");
            return null;
        }
        return configLoader.getBaseConfig();
    }

    public static void main(String[] args) {

        BaseConfig baseConfig = getBaseConfig();
        if (baseConfig == null) return;

        DataStore dataStore = new DataStore();
        GameControl gameControl = new GameControl();

        MainService mainService = new MainService();
        mainService.initData(baseConfig.rows, baseConfig.cols, baseConfig.mine);
        gameControl.setMainService(mainService);
        mainService.copyToDataCenter(dataStore);
        gameControl.setDataStore(dataStore);
        MainFrame frame = new MainFrame(baseConfig.title, dataStore, gameControl);
        gameControl.setFrame(frame);

        frame.showCenter();


    }
}
