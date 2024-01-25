package io.blackjesus.calendario.test;

import io.blackjesus.calendario.models.DummyClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummyClassTest {
    @Test
    void testGetterAndSetter() {
        DummyClass dummy = new DummyClass("Test", 10, true);
        assertEquals("Test", dummy.getName());
        assertEquals(10, dummy.getValue());
        assertTrue(dummy.isFlag());

        dummy.setName("Updated");
        dummy.setValue(20);
        dummy.setFlag(false);

        assertEquals("Updated", dummy.getName());
        assertEquals(20, dummy.getValue());
        assertFalse(dummy.isFlag());
    }

    @Test
    void testIncrementValue() {
        DummyClass dummy = new DummyClass("Test", 10, true);
        dummy.incrementValue();
        assertEquals(11, dummy.getValue());

        // Többszöri meghívás tesztelése
        dummy.incrementValue();
        dummy.incrementValue();
        assertEquals(13, dummy.getValue());
    }

    @Test
    void testGetInfo() {
        DummyClass dummy = new DummyClass("Test", 10, true);
        assertEquals("Name: Test, Value: 10, Flag: true", dummy.getInfo());

        // Értékek módosítása
        dummy.setName("Updated");
        dummy.setValue(20);
        dummy.setFlag(false);

        assertEquals("Name: Updated, Value: 20, Flag: false", dummy.getInfo());
    }
}