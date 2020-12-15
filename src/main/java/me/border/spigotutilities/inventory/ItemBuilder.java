package me.border.spigotutilities.inventory;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.mojang.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {

    private static final Enchantment glowEnchantment = UtilsMain.registerGlowEnchantment();

    private Material type = null;
    private String name = null;
    private List<String> lore = null;
    private int amount = 0;
    private Map<Enchantment, Integer> enchantments = null;
    private String skull = null;
    private UUID skullID = null;
    private boolean glowing = false;

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

    public ItemBuilder setType(Material type){
        this.type = type;
        return this;
    }

    public Material getType(){
        return type;
    }

    public ItemBuilder setName(String name){
        this.name = name;
        return this;
    }

    public String getName(){
        return name;
    }

    public ItemBuilder setLore(List<String> lore){
        this.lore = lore;
        return this;
    }

    public ItemBuilder addLine(String line){
        if (lore == null)
            lore = new ArrayList<>();
        this.lore.add(line);
        return this;
    }

    public List<String> getLore(){
        return lore;
    }

    public ItemBuilder setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public int getAmount(){
        return amount;

    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments){
        this.enchantments = enchantments;
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing){
        this.glowing = glowing;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        if (enchantments == null)
            this.enchantments = new HashMap<>();
        this.enchantments.put(enchantment, level);
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments(){
        return this.enchantments;
    }

    public ItemBuilder setSkull(String name){
        this.skull = skull;
        return this;
    }

    public String getSkull(){
        return skull;
    }

    public ItemBuilder setSkullID(UUID uuid){
        this.skullID = uuid;
        return this;
    }

    public UUID getSkullID(){
        return skullID;
    }

    public boolean isGlowing() {
        return glowing;
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
        if (glowing){
            itemStack.addUnsafeEnchantment(glowEnchantment, 0);
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
            try {
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(PlayerInfo.getUUID(skull)));
            } catch (NullPointerException e){
                Bukkit.getLogger().warning("Failed to create item due to \"" + skull + "\" Not being a valid name");
            }
        }
        if (skullID != null){
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(skullID));
        }
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
