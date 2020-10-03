package minesweeper.common;

import minesweeper.core.DataStore;

public interface DataChangeListener {
    void dataChanged(DataStore source);
}
