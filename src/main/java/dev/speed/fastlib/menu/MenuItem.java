package dev.speed.fastlib.menu;

import dev.speed.fastlib.configuration.Serializable;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MenuItem implements Serializable {

    private final String key;
    private final List<Integer> slots;
    private final Item item;

    public Map<String, Object> serialize(){

        Map<String, Object> map = new HashMap<>();

        map.put("key", key);

        Map<String, Object> itemMap = item.serialize();

        for(String key: itemMap.keySet()){
            map.put("item." + key, itemMap.get(key));
        }

        List<String> slotStringList = new ArrayList<>();
        for(int slot: slots){
            slotStringList.add(String.valueOf(slot));
        }

        map.put("slots", slotStringList);

        return map;
    }
    public static MenuItem deserialize(YamlFile yamlFile, String path){
        YamlConfiguration c = yamlFile.getConfig();
        String key = c.getString(path + ".key");
        Item item = Item.deserialize(yamlFile, path + ".item");
        List<String> slotStringList = c.getStringList(path + ".slots");
        List<Integer> slots = new ArrayList<>();
        for(String slotString: slotStringList){
            int slot;
            try {
                slot = Integer.parseInt(slotString);
            } catch (NumberFormatException e){
                continue;
            }
            slots.add(slot);
        }
        return new MenuItem(key, slots, item);
    }

}
