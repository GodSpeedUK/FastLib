package dev.speed.fastlib.tempdata.impl;

import dev.speed.fastlib.menu.Menu;
import dev.speed.fastlib.tempdata.data.TempData;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MenuTempData extends TempData {

    private Menu currentMenu = null;
    private int currentPage = 0;
    public MenuTempData(UUID user) {
        super(user);
    }
}
