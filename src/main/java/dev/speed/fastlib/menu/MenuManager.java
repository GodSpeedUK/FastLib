package dev.speed.fastlib.menu;

import dev.speed.fastlib.util.Manager;

public class MenuManager extends Manager<String, Menu> {
    @Override
    public String getKey(Menu obj) {
        return obj.getId().toLowerCase();
    }
}
