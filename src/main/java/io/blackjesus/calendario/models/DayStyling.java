package io.blackjesus.calendario.models;

import javafx.scene.layout.BorderWidths;

public class DayStyling {
    private boolean isTopRow, isFirstColumn, isLastColumn, isBottomRow, isToday;

    public DayStyling() {
    }

    public DayStyling(boolean isTopRow, boolean isFirstColumn, boolean isLastColumn, boolean isBottomRow) {
        this.isTopRow = isTopRow;
        this.isFirstColumn = isFirstColumn;
        this.isLastColumn = isLastColumn;
        this.isBottomRow = isBottomRow;
    }

    public BorderWidths getBorderWidths() {
        int top = isTopRow ? 1 : 0;
        int right = isLastColumn ? 1 : 0;
        int left = 1;
        int bottom = 1;
        return new BorderWidths(top, right, left, bottom);
    }

    public boolean isTopRow() {
        return isTopRow;
    }

    public void setTopRow(boolean topRow) {
        isTopRow = topRow;
    }

    public boolean isFirstColumn() {
        return isFirstColumn;
    }

    public void setFirstColumn(boolean firstColumn) {
        isFirstColumn = firstColumn;
    }

    public boolean isLastColumn() {
        return isLastColumn;
    }

    public void setLastColumn(boolean lastColumn) {
        isLastColumn = lastColumn;
    }

    public boolean isBottomRow() {
        return isBottomRow;
    }

    public void setBottomRow(boolean bottomRow) {
        isBottomRow = bottomRow;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }
}
