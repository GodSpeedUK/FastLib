package dev.speed.fastlib.item;


import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.speed.fastlib.configuration.Serializable;
import dev.speed.fastlib.file.YamlFile;
import dev.speed.fastlib.util.Placeholder;
import dev.speed.fastlib.util.Text;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.*;

public class Item implements Serializable {

    private String material;
    private String name;
    private List<String> lore;
    private byte data = 0;
    private Map<String, String> nbtStrings;
    private Map<String, Integer> nbtIntegers;
    private Map<String, Double> nbtDoubles;
    private Map<Enchantment, Integer> enchantments;
    private List<ItemFlag> itemFlags;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("material", material);
        map.put("data", (int) data);
        if (name != null) {
            map.put("name", name);
        }
        if (lore != null) {
            map.put("lore", lore);
        }
        if (nbtStrings != null) {
            for (String string : nbtStrings.keySet()) {
                map.put("nbtStrings." + string, nbtStrings.get(string));
            }
        }

        if (nbtIntegers != null) {
            for (String string : nbtIntegers.keySet()) {
                map.put("nbtIntegers." + string, nbtIntegers.get(string));
            }
        }

        if (nbtDoubles != null) {
            for (String string : nbtDoubles.keySet()) {
                map.put("nbtDoubles." + string, nbtDoubles.get(string));
            }
        }

        if (enchantments != null) {
            for (Enchantment enchantment : enchantments.keySet()) {
                map.put("enchantments." + enchantment.getKey().getKey(), enchantments.get(enchantment));
            }
        }

        if (itemFlags != null) {
            List<String> itemFlagStrings = new ArrayList<>();
            for (ItemFlag itemFlag : itemFlags) {
                itemFlagStrings.add(itemFlag.name());
            }
            map.put("itemFlags", itemFlagStrings);
        }

        return map;
    }

    public Item material(String material) {
        this.material = material;
        return this;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public Item lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public Item data(byte data) {
        this.data = data;
        return this;
    }

    public Item nbtString(String key, String value) {
        if (this.nbtStrings == null) {
            this.nbtStrings = new HashMap<>();
        }

        this.nbtStrings.put(key, value);
        return this;
    }

    public Item nbtInteger(String key, Integer value) {
        if (this.nbtIntegers == null) {
            this.nbtIntegers = new HashMap<>();
        }

        this.nbtIntegers.put(key, value);
        return this;
    }

    public Item nbtDouble(String key, Double value) {
        if (this.nbtDoubles == null) {
            this.nbtDoubles = new HashMap<>();
        }

        this.nbtDoubles.put(key, value);
        return this;
    }

    public Item enchantment(Enchantment enchantment, Integer level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<>();
        }

        this.enchantments.put(enchantment, level);
        return this;
    }

    public Item itemFlags(ItemFlag... itemFlags) {
        this.itemFlags = Arrays.asList(itemFlags);
        return this;
    }

    public ItemStack toItemStack(Placeholder... placeholders){
        Material material = Material.getMaterial(this.material.toUpperCase());
        if(material == null){
            return null;
        }

        ItemStack itemStack = new ItemStack(material, 1, data);

        NBTItem nbtItem = new NBTItem(itemStack);

        if(nbtStrings != null){
            for(String string : nbtStrings.keySet()){
                nbtItem.setString(string, nbtStrings.get(string));
            }
        }

        if(nbtIntegers != null){
            for(String string : nbtIntegers.keySet()){
                nbtItem.setInteger(string, nbtIntegers.get(string));
            }
        }

        if(nbtDoubles != null){
            for(String string : nbtDoubles.keySet()){
                nbtItem.setDouble(string, nbtDoubles.get(string));
            }
        }

        itemStack = nbtItem.getItem();

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(Text.c(Placeholder.apply(name, placeholders)));
        }

        if (lore != null) {
            List<String> lore = new ArrayList<>();
            for (String string : this.lore) {
                lore.add(Text.c(Placeholder.apply(string, placeholders)));
            }
            itemMeta.setLore(lore);
        }


        if(itemFlags != null){
            for(ItemFlag itemFlag : itemFlags){
                itemMeta.addItemFlags(itemFlag);
            }
        }

        if(enchantments != null){
            for(Enchantment enchantment : enchantments.keySet()){
                itemMeta.addEnchant(enchantment, enchantments.get(enchantment), true);
            }
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static Item fromItemStack(ItemStack itemStack){

        Item item = new Item();
        Material material = itemStack.getType();
        item.material(material.name());
        item.data(itemStack.getData().getData());

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(itemMeta.hasDisplayName()){
            item.name(itemMeta.getDisplayName());
        }

        if(itemMeta.hasLore()){
            item.lore(itemMeta.getLore().toArray(new String[0]));
        }

        NBTItem nbtItem = new NBTItem(itemStack);

        for(String key: nbtItem.getKeys()){
            switch (nbtItem.getType(key)){
                case NBTTagString:
                    item.nbtString(key, nbtItem.getString(key));
                    break;
                case NBTTagInt:
                    item.nbtInteger(key, nbtItem.getInteger(key));
                    break;
                case NBTTagDouble:
                    item.nbtDouble(key, nbtItem.getDouble(key));
                    break;
            }
        }

        if(!itemMeta.getItemFlags().isEmpty()){
            item.itemFlags(itemMeta.getItemFlags().toArray(new ItemFlag[0]));
        }

        if(itemStack.getEnchantments().size() != 0){
            for(Enchantment enchantment : itemStack.getEnchantments().keySet()){
                item.enchantment(enchantment, itemStack.getEnchantments().get(enchantment));
            }
        }

        return item;

    }

    public static Item deserialize(YamlFile yamlFile, String path){
        YamlConfiguration c = yamlFile.getConfig();
        if(!c.contains(path + ".material")){
            return null;
        }

        Item item = new Item();

        item.material(c.getString(path + ".material"));

        if(c.contains(path + ".data")){
            item.data((byte) c.getInt(path + ".data"));
        }

        if(c.contains(path + ".name")){
            item.name(c.getString(path + ".name"));
        }

        if(c.contains(path + ".lore")){
            item.lore(c.getStringList(path + ".lore").toArray(new String[0]));
        }

        if(c.contains(path + ".nbtStrings")){
            for(String string : c.getConfigurationSection(path + ".nbtStrings").getKeys(false)){
                item.nbtString(string, c.getString(path + ".nbtStrings." + string));
            }
        }

        if(c.contains(path + ".nbtIntegers")){
            for(String string : c.getConfigurationSection(path + ".nbtIntegers").getKeys(false)){
                item.nbtInteger(string, c.getInt(path + ".nbtIntegers." + string));
            }
        }

        if(c.contains(path + ".nbtDoubles")){
            for(String string : c.getConfigurationSection(path + ".nbtDoubles").getKeys(false)){
                item.nbtDouble(string, c.getDouble(path + ".nbtDoubles." + string));
            }
        }

        if(c.contains(path + ".enchantments")){
            for(String string : c.getConfigurationSection(path + ".enchantments").getKeys(false)){

                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(string));

                if(enchantment == null){
                    continue;
                }

                item.enchantment(enchantment, c.getInt(path + ".enchantments." + string));
            }
        }

        if(c.contains(path + ".itemFlags")){
            List<ItemFlag> itemFlags = new ArrayList<>();
            for(String string : c.getStringList(path + ".itemFlags")){
                itemFlags.add(ItemFlag.valueOf(string));
            }
            item.itemFlags(itemFlags.toArray(new ItemFlag[0]));
        }

        return item;

    }

}
