package minesweeper.core;

import minesweeper.gui.ConfigDialog;
import minesweeper.gui.MainFrame;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * @author godghdai
 * #Description GameControl
 * #Date: 2020/9/27 14:33
 */
public class GameControl {
    private MainFrame frame;
    private MainService mainService;
    private DataCenter dataCenter;

    public GameControl() {
    }

    private void restart(String msg) {
        frame.repaint();
        int res = JOptionPane.showConfirmDialog(null, msg, "提示", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            mainService.start(dataCenter);
            frame.dataChanged(dataCenter);
        }
    }

    public void onMouseReleased(MouseEvent event) {
        //鼠标右键单击
        if (event.getModifiers() == InputEvent.BUTTON3_MASK) {
            mainService.onRightMouseReleased(event);
            frame.setRemain(dataCenter.mineCount - mainService.countRight());

        } else if (mainService.onLeftMouseReleased(event)) {
            restart("你输了，是否重新开始？？");
        }

        if (mainService.isWin()) {
            restart("你赢了，是否重新开始？？");
        }
        frame.repaint();
    }

    /**
     * 显示设置窗口
     */
    public void onClickedSetting() {
        ConfigDialog dialog = new ConfigDialog(frame);
        dialog.setControl(this);
        dialog.setValues(dataCenter.maxY, dataCenter.maxX, dataCenter.mineCount);
        dialog.display();
    }

    /**
     * 设置窗口 确认回调
     *
     * @param rows      行数
     * @param cols      列数
     * @param mineCount 雷数
     * @return 是否设置成功
     */
    public boolean onModifySetting(int rows, int cols, int mineCount) {
        //单元格总数必须大于等于雷数，且不为0
        if (rows * cols >= mineCount && mineCount != 0) {
            mainService.initData(rows, cols, mineCount);
            mainService.copyToDataCenter(dataCenter);
            frame.dataChanged(dataCenter);
            return true;
        }
        String msg="行*列的值必须大于等于" + mineCount + "!!";
        JOptionPane.showMessageDialog(null,msg,"提示", JOptionPane.ERROR_MESSAGE);
        return false;
    }


    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public void setDataCenter(DataCenter dataCenter) {
        this.dataCenter = dataCenter;
    }

}
