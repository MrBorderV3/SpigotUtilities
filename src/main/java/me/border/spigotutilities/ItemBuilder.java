package me.border.spigotutilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {

    private Material type = null;
    private String name = null;
    private List<String> lore = null;
    private int amount = 0;
    private Map<Enchantment, Integer> enchantments;
    private String skull = null;
    private UUID skullID = null;

    public ItemBuilder(){
    }

    public ItemBuilder(Material type){
        setType(type);
    }

    public ItemBuilder(Material type, String name){
        this(type);
        setName(name);
    }

    public ItemBuilder(Material type, String name, List<String> lore){
        this(type, name);
        setLore(lore);
    }

    public ItemBuilder(ItemStack itemStack){
        this(itemStack.getType());
        setAmount(itemStack.getAmount());
        if (itemStack.hasItemMeta()){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()){
                setName(itemMeta.getDisplayName());
            }
            if (itemMeta.hasLore()){
                setLore(itemMeta.getLore());
            }
            if (itemMeta.hasEnchants()){
                setEnchantments(itemMeta.getEnchants());
            }
        }
    }

    public void setType(Material type){
        this.type = type;
    }

    public Material getType(){
        return type;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setLore(List<String> lore){
        this.lore = lore;
    }

    public void addLine(String line){
        if (lore == null)
            lore = new ArrayList<>();
        this.lore.add(line);
    }

    public List<String> getLore(){
        return lore;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments){
        this.enchantments = enchantments;
    }

    public void addEnchantment(Enchantment enchantment, int level){
        if (enchantments == null)
            this.enchantments = new HashMap<>();
        this.enchantments.put(enchantment, level);
    }

    public Map<Enchantment, Integer> getEnchantments(){
        return this.enchantments;
    }

    public void setSkull(String name){
        this.skull = skull;
    }

    public String getSkull(){
        return skull;
    }

    public void setSkullID(UUID uuid){
        this.skullID = uuid;
    }

    public UUID getSkullID(){
        return skullID;
    }

    public ItemStack build(){
        ItemStack itemStack = new ItemStack(type, amount == 0 ? 1 : amount);
        ItemMeta meta = itemStack.getItemMeta();
        if (name != null){
            meta.setDisplayName(name);
        }
        if (lore != null){
            meta.setLore(lore);
        }
        if (enchantments != null){
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()){
                meta.addEnchant(entry.getKey(), entry.getValue(), false);
            }
        }
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemStack buildSkull(){
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, amount == 0 ? 1 : amount);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        if (name != null){
            meta.setDisplayName(name);
        }
        if (lore != null){
            meta.setLore(lore);
        }
        if (enchantments != null){
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()){
                meta.addEnchant(entry.getKey(), entry.getValue(), false);
            }
        }
        if (skull != null){
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(skull));
        }
        if (skullID != null){
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(skullID));
        }
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
