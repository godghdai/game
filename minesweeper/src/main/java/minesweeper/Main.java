package minesweeper;

import minesweeper.core.DataCenter;
import minesweeper.core.GameControl;
import minesweeper.core.MainService;
import minesweeper.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int size = 32;
        DataCenter dataCenter = new DataCenter();
        GameControl gameControl = new GameControl();

        MainService mainService = new MainService();
        mainService.initData(rows , cols ,16);
        gameControl.setMainService(mainService);
        mainService.copyToDataCenter(dataCenter);
        gameControl.setDataCenter(dataCenter);
        MainFrame frame = new MainFrame("扫雷", cols * size, rows * size, dataCenter, gameControl);
        gameControl.setFrame(frame);

        frame.showCenter();
    }
}
