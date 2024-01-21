package dev.speed.fastlib.menu;

import org.bukkit.entity.Player;

public abstract class MenuInteractionHandler {

    public abstract boolean onClick(Player player, MenuItem menuItem);

    public abstract void onClose(Player player, Menu menu);

}
