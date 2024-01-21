package dev.speed.fastlib.menu.listener;

import dev.speed.fastlib.menu.Menu;
import dev.speed.fastlib.menu.MenuItem;
import dev.speed.fastlib.tempdata.impl.MenuTempData;
import dev.speed.fastlib.user.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        if(e.getClickedInventory() == null) return;

        if(!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        User user = User.get(player.getUniqueId());

        MenuTempData menuTempData = user.getTempData(MenuTempData.class);

        if(menuTempData.getCurrentMenu() == null){
            return;
        }

        Menu menu = menuTempData.getCurrentMenu();

        int slot = e.getSlot();

        MenuItem menuItem = menu.getClickedItem(slot);

        if(menuItem == null){
            return;
        }

        if(menu.getMenuInteractionHandler() == null){
            return;
        }

        e.setCancelled(menu.getMenuInteractionHandler().onClick(player, menuItem));
    }

}
