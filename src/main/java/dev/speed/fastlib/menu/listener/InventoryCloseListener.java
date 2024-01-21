package dev.speed.fastlib.menu.listener;

import dev.speed.fastlib.FastLib;
import dev.speed.fastlib.menu.Menu;
import dev.speed.fastlib.menu.MenuItem;
import dev.speed.fastlib.tempdata.impl.MenuTempData;
import dev.speed.fastlib.user.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        User user = User.get(e.getPlayer().getUniqueId());

        MenuTempData menuTempData = user.getTempData(MenuTempData.class);


        Menu currentMenu = menuTempData.getCurrentMenu();

        if (currentMenu == null) {
            return;
        }

        menuTempData.setCurrentMenu(null);


        if (currentMenu.getMenuInteractionHandler() == null) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(FastLib.getInstance(), () -> {
            currentMenu.getMenuInteractionHandler().onClose((Player) e.getPlayer(), currentMenu);
        }, 1L);

    }

}
