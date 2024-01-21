package dev.speed.fastlib.menu;

import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.Text;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Getter
public abstract class Menu {

    private final String id;
    private final String name;

    private String previousMenuId = null;

    private final int size;
    private List<MenuItem> defaultMenuItemList;
    private List<MenuItem> additionalMenuItemList;

    @Setter
    private MenuInteractionHandler menuInteractionHandler;


    public Menu(String id, String name, int size){
        this.id = id;
        this.name = name;
        this.size = size;
        this.defaultMenuItemList = new ArrayList<>();
        this.additionalMenuItemList = new ArrayList<>();
    }

    public Menu(String id, String name, int size, String previousMenuId) {
        this(id, name, size);
        this.previousMenuId = previousMenuId;
    }

    public Map<String, Object> basicSerialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("size", this.size);
        if(this.previousMenuId != null){
            map.put("previousMenuId", this.previousMenuId);
        }

        if(!this.defaultMenuItemList.isEmpty()) {
            for (MenuItem menuItem : this.defaultMenuItemList) {
                Map<String, Object> serialized = menuItem.serialize();
                for (String key : serialized.keySet()) {
                    map.put("defaultMenuItemList." + menuItem.getKey() + "." + key, serialized.get(key));
                }
            }
        }
        return map;
    }



    public Inventory createInventory(Placeholder... placeholders){
        Inventory inventory = Bukkit.createInventory(null, this.size, Text.c(Placeholder.apply(this.name, placeholders)));
        for(MenuItem menuItem: this.defaultMenuItemList){
            for(int slot: menuItem.getSlots()){
                inventory.setItem(slot, menuItem.getItem().toItemStack(placeholders));
            }
        }
        for(MenuItem menuItem: this.additionalMenuItemList){
            for(int slot: menuItem.getSlots()){
                inventory.setItem(slot, menuItem.getItem().toItemStack(placeholders));
            }
        }
        return inventory;
    }

    public abstract MenuItem getClickedItem(int slot);

    public abstract void open(Player player, Placeholder... placeholders);

    public void addItems(MenuItem... menuItems){
        this.additionalMenuItemList.addAll(Arrays.asList(menuItems));
    }

    public void setDefaultMenuItemList(List<MenuItem> defaultMenuItemList){
        this.defaultMenuItemList = defaultMenuItemList;
    }



}
