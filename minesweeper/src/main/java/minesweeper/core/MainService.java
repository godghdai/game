package minesweeper.core;

import minesweeper.util.ClassUtil;

import java.awt.event.MouseEvent;

/**
 * @author godghdai
 * #Description Service
 * #Date: 2020/9/28 13:54
 */
public class MainService {
    private static int MINE_NUMBER = -1; //-1 为有雷
    //3*3中间周围的8个向量
    private static int[][] dirs = new int[][]{
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0},/*{}*/{1, 0},
            {-1, 1}, {0, 1}, {1, 1},
    };
    private static int size = 32;
    private int[][] data;
    private boolean[][] dataOpen;//打开标记
    private boolean[][] dataFlag;//插旗标记
    private int maxX;
    private int maxY;
    private int mineCount;

    public MainService() {
    }

    /**
     * 验证坐标是否越界
     */
    private boolean isInMap(int x, int y) {
        return x >= 0 && x <= (maxX - 1) && y >= 0 && y <= (maxY - 1);
    }

    /**
     * 随机生成雷区，并打乱
     *
     * @param rows 行
     * @param cols 列
     * @return 打乱的一维数组
     */
    private int[] randomShuffle(int rows, int cols) {
        int[] array = new int[rows * cols];
        for (int i = 0; i < mineCount; i++) {
            array[i] = MINE_NUMBER;
        }
        data = new int[rows][cols];

        for (int left = 0; left < array.length; left++) {
            int index = (int) (Math.random() * (array.length - left)) + left;
            int temp = array[left];
            array[left] = array[index];
            array[index] = temp;
        }
        return array;
    }

    public void initData(int rows, int cols, int mineCount) {
        this.mineCount = mineCount;
        int[] array = randomShuffle(rows, cols);
        //一维数组转二维
        for (int left = 0; left < array.length; left++) {
            int row = left / cols;
            int col = left % cols;
            data[row][col] = array[left];
        }

        maxY = rows;
        maxX = cols;
        dataOpen = new boolean[maxY][maxX];
        dataFlag = new boolean[maxY][maxX];
        countRangeMineNums();
    }

    void start(DataStore dataStore) {
        initData(maxY, maxX, mineCount);
        ClassUtil.copyProperty(dataStore, this);
    }

    public void copyToDataCenter(DataStore dataStore) {
        ClassUtil.copyProperty(dataStore, this);
    }

    /**
     * 递归调用来开启没雷的一片区域
     *
     * @param x X坐标
     * @param y Y坐标
     */
    private void open(int x, int y) {
        for (int yy = y - 1; yy <= y + 1; yy++) {
            for (int xx = x - 1; xx <= x + 1; xx++) {
                if (!isInMap(xx, yy) || dataOpen[yy][xx]) continue;
                dataOpen[yy][xx] = true;
                if (data[yy][xx] > 0) continue;
                open(xx, yy);
            }
        }
    }

    /**
     * 统计单元格周围的雷数
     */
    private void countRangeMineNums() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (data[y][x] != MINE_NUMBER) continue;
                for (int[] dir : dirs) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                    if (!isInMap(newX, newY) || data[newY][newX] == MINE_NUMBER) continue;
                    data[newY][newX] += 1;
                }
            }
        }

    }

    boolean onLeftMouseReleased(MouseEvent event) {
        int x = event.getX() / size;
        int y = event.getY() / size;
        if (!isInMap(x, y) || dataOpen[y][x] || dataFlag[y][x]) return false;

        if (data[y][x] == 0) {
            open(x, y);
        }

        if (data[y][x] == MINE_NUMBER) {
            dataOpen[y][x] = true;
            return true;
        }
        dataOpen[y][x] = true;
        return false;

    }

    /**
     * 判断是否胜利
     *
     * @return 胜利 True
     */
    boolean isWin() {
        int flagCount = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (data[y][x] == MINE_NUMBER && !dataFlag[y][x]) {
                    return false;
                } else if (dataFlag[y][x]) {
                    flagCount++;
                }
            }
        }
        //旗子的数量是否与雷数相等
        return flagCount == mineCount;
    }

    int countRight() {
        int rightCount = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (data[y][x] == MINE_NUMBER && dataFlag[y][x]) {
                    rightCount++;
                }
            }
        }
        return rightCount;
    }

    /**
     * 插旗子
     **/
    boolean onRightMouseReleased(MouseEvent event) {
        int x = event.getX() / size;
        int y = event.getY() / size;
        if (!isInMap(x, y)) return false;
       // if(dataOpen[y][x]) return false;
        dataFlag[y][x] = !dataFlag[y][x];
        return true;
    }

}
