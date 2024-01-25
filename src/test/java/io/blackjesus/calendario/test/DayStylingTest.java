package io.blackjesus.calendario.test;

import io.blackjesus.calendario.models.DayStyling;
import javafx.scene.layout.BorderWidths;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayStylingTest {

    @Test
    void testDefaultConstructor() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isTopRow());
        assertFalse(styling.isFirstColumn());
        assertFalse(styling.isLastColumn());
        assertFalse(styling.isBottomRow());
        assertFalse(styling.isToday());
    }

    @Test
    void testParameterizedConstructor() {
        DayStyling styling = new DayStyling(true, false, true, false);
        assertTrue(styling.isTopRow());
        assertFalse(styling.isFirstColumn());
        assertTrue(styling.isLastColumn());
        assertFalse(styling.isBottomRow());
        assertFalse(styling.isToday());
    }

    @Test
    void testGetBorderWidths() {
        DayStyling styling = new DayStyling(true, false, true, false);
        BorderWidths borderWidths = styling.getBorderWidths();
        assertEquals(1, borderWidths.getTop());
        assertEquals(1, borderWidths.getRight());
        assertEquals(1, borderWidths.getLeft());
        assertEquals(1, borderWidths.getBottom());
    }

    @Test
    void testSetTopRow() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isTopRow());
        styling.setTopRow(true);
        assertTrue(styling.isTopRow());
    }

    @Test
    void testSetFirstColumn() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isFirstColumn());
        styling.setFirstColumn(true);
        assertTrue(styling.isFirstColumn());
    }

    @Test
    void testSetLastColumn() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isLastColumn());
        styling.setLastColumn(true);
        assertTrue(styling.isLastColumn());
    }

    @Test
    void testSetBottomRow() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isBottomRow());
        styling.setBottomRow(true);
        assertTrue(styling.isBottomRow());
    }

    @Test
    void testSetToday() {
        DayStyling styling = new DayStyling();
        assertFalse(styling.isToday());
        styling.setToday(true);
        assertTrue(styling.isToday());
    }

}