package minesweeper.common;

import minesweeper.core.DataCenter;

public interface DataChangeListener {
    void dataChanged(DataCenter source);
}
