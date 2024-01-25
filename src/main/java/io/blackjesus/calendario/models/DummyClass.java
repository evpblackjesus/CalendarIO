package io.blackjesus.calendario.models;

public class DummyClass {
    private String name;
    private int value;
    private boolean flag;

    public DummyClass(String name, int value, boolean flag) {
        this.name = name;
        this.value = value;
        this.flag = flag;
    }

    // Getterek és setterek
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    // Egyéb metódusok
    public void incrementValue() {
        this.value++;
    }

    public String getInfo() {
        return "Name: " + name + ", Value: " + value + ", Flag: " + flag;
    }
}

