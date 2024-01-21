package dev.speed.fastlib.menu;

import dev.speed.fastlib.configuration.Serializable;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.tempdata.impl.MenuTempData;
import dev.speed.fastlib.user.user.User;
import dev.speed.fastlib.util.Placeholder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DefaultMenu extends Menu implements Serializable, Cloneable {
    public DefaultMenu(String id, String name, int size) {
        super(id, name, size);
    }

    public DefaultMenu(String id, String name, int size, String previousMenuId) {
        super(id, name, size, previousMenuId);
    }


    @Override
    public MenuItem getClickedItem(int slot) {

        if (this.getDefaultMenuItemList() != null && !this.getDefaultMenuItemList().isEmpty()) {
            for (MenuItem menuItem : this.getDefaultMenuItemList()) {
                if (menuItem.getSlots().contains(slot)) {
                    return menuItem;
                }
            }
        }

        if(this.getAdditionalMenuItemList() == null || this.getAdditionalMenuItemList().isEmpty()){
            return null;
        }
        for (MenuItem menuItem : this.getAdditionalMenuItemList()) {
            if (menuItem.getSlots().contains(slot)) {
                return menuItem;
            }
        }
        return null;
    }

    public Map<String, Object> serialize() {
        return this.basicSerialize();
    }

    public DefaultMenu addDefaultMenuItems(MenuItem... menuItems) {
        this.setDefaultMenuItemList(Arrays.asList(menuItems));
        return this;
    }

    public static DefaultMenu deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        String id = c.getString(path + ".id");
        String name = c.getString(path + ".name");
        int size = c.getInt(path + ".size");
        if (size % 9 != 0) {
            size = 9;
        }

        DefaultMenu defaultMenu;

        if (c.contains(path + ".previousMenuId")) {
            String previousMenuId = c.getString(path + ".previousMenuId");
            defaultMenu = new DefaultMenu(id, name, size, previousMenuId);
        } else {
            defaultMenu = new DefaultMenu(id, name, size);
        }

        List<MenuItem> defaultMenuItemList = new ArrayList<>();

        if (c.contains(path + ".defaultMenuItemList")) {
            for (String key : c.getConfigurationSection(path + ".defaultMenuItemList").getKeys(false)) {
                MenuItem menuItem = MenuItem.deserialize(yamlFile, path + ".defaultMenuItemList." + key);
                defaultMenuItemList.add(menuItem);
            }
        }

        defaultMenu.setDefaultMenuItemList(defaultMenuItemList);

        return defaultMenu;
    }

    @Override
    public void open(Player player, Placeholder... placeholders) {
        Inventory inventory = this.createInventory(placeholders);
        player.openInventory(inventory);
        User user = User.get(player.getUniqueId());
        MenuTempData menuTempData = user.getTempData(MenuTempData.class);
        menuTempData.setCurrentMenu(this);
    }


    public List<Integer> getEmptySlots() {
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            if (this.getClickedItem(i) == null) {
                emptySlots.add(i);
            }
        }
        return emptySlots;
    }

    @Override
    public DefaultMenu clone() {

        DefaultMenu cloneMenu;

        if (this.getPreviousMenuId() != null) {
            cloneMenu = new DefaultMenu(this.getId(), this.getName(), this.getSize(), this.getPreviousMenuId());
        } else {
            cloneMenu = new DefaultMenu(this.getId(), this.getName(), this.getSize());
        }

        cloneMenu.setDefaultMenuItemList(this.getDefaultMenuItemList());
        cloneMenu.setMenuInteractionHandler(this.getMenuInteractionHandler());
        return cloneMenu;
    }
}
