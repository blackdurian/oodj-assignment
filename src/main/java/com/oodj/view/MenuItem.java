package com.oodj.view;

public class MenuItem {

    private String name;
    private String[] keys;
    private MenuEvent menuEvent;

    public MenuItem(String name, String[] keys, MenuEvent event) {
        this.name = name;
        this.keys = keys;
        this.menuEvent = event;
    }

    public MenuEvent getMenuEvent() {
        return menuEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getKeys() {
        return keys;
    }


}
