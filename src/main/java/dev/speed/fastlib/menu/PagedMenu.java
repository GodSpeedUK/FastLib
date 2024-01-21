package dev.speed.fastlib.menu;

import dev.speed.fastlib.util.Placeholder;
import org.bukkit.entity.Player;

public class PagedMenu extends Menu{
    public PagedMenu(String id, String name, int size) {
        super(id, name, size);
    }

    @Override
    public MenuItem getClickedItem(int slot) {
        return null;
    }

    @Override
    public void open(Player player, Placeholder... placeholders) {

    }

    public void showPage(Player player, int page){

    }

}
