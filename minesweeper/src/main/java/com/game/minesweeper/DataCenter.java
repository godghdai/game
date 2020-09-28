package com.game.minesweeper;

import com.game.minesweeper.util.Copy;

/**
 * @author godghdai
 * #Description DataCenter
 * #Date: 2020/9/27 14:12
 */
public class DataCenter{
    @Copy
    public int size;
    @Copy
    public int maxX;
    @Copy
    public int maxY;
    @Copy
    public int[][] data;
    @Copy
    public boolean[][] dataOpen;
    @Copy
    public boolean[][] dataFlag;
    @Copy
    public int mineCount;
}
