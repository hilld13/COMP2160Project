package com.lazymail.comp2160project;

public class list_item {

    int background;
    String itemName;

    public list_item(int background, String itemName) {
        this.background = background;
        this.itemName = itemName;
    }

    public int getBackground() {
        return background;
    }

    public String getItemName() {
        return itemName;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
