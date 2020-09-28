package com.game.minesweeper;

public class Main {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int size = 32;
        DataCenter dataCenter = new DataCenter();
        GameControl gameControl = new GameControl();

        MainService mainService = new MainService();
        mainService.initData(rows , cols ,20);
        gameControl.setMainService(mainService);
        mainService.copyToDataCenter(dataCenter);
        gameControl.setDataCenter(dataCenter);
        MineFrame frame = new MineFrame("扫雷", cols * size, rows * size, dataCenter, gameControl);
        gameControl.setFrame(frame);

        frame.showCenter();
    }
}
