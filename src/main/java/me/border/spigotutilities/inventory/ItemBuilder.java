package me.border.spigotutilities.inventory;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.mojang.PlayerInfo;
import me.border.utilities.interfaces.Builder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A {@link Builder} for easier creation of {@link ItemStack}s
 */
public class ItemBuilder implements Builder<ItemStack> {

    public static void registerGlow(){
        glowEnchantment = registerGlowEnchantment();
    }

    private static Enchantment glowEnchantment;

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
            if (itemStack.hasItemMeta()) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    setName(itemMeta.getDisplayName());
                }
                if (itemMeta.hasLore()) {
                    setLore(itemMeta.getLore());
                }
                if (itemMeta.hasEnchants()) {
                    setEnchantments(itemMeta.getEnchants());
                }
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

    public ItemStack build() {
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

    /**
     * Compares two ItemStacks based on their name, lore, and type
     *
     * @param o First ItemStack
     * @param o2 Second ItemStack
     *
     * @return Whether the items' name, lore, and type matched
     */
    public static boolean compareItems(ItemStack o, ItemStack o2){
        if (!o.getType().equals(o2.getType()))
            return false;

        if (o.hasItemMeta() != o2.hasItemMeta())
            return false;
        if (o.hasItemMeta()){
            ItemMeta oMeta = o.getItemMeta();
            ItemMeta o2Meta = o2.getItemMeta();

            if (oMeta.hasDisplayName() != o2Meta.hasDisplayName())
                return false;
            if (oMeta.hasDisplayName()) {
                String oName = oMeta.getDisplayName();
                String o2Name = o2Meta.getDisplayName();
                if (!oName.equals(o2Name))
                    return false;
            }

            if (oMeta.hasLore() != o2Meta.hasLore())
                return false;
            if (oMeta.hasLore()) {
                List<String> oLore = oMeta.getLore();
                List<String> o2Lore = o2Meta.getLore();
                if (oLore.size() != o2Lore.size())
                    return false;
                int line = 0;
                for (String oLine : oLore){
                    String o2Line = o2Lore.get(line);
                    if (!o2Line.equals(oLine))
                        return false;
                    line++;
                }
            }
        }

        return true;
    }

    public static Enchantment registerGlowEnchantment(){
        Enchantment glow = Enchantment.getByKey(new NamespacedKey(UtilsMain.getInstance(), "Glow"));
        if (glow != null) {
            if (glow.getKey().getKey().equals("Glow")) {
                return glow;
            }
        }

        Field f;
        boolean forced = false;
        if (!Enchantment.isAcceptingRegistrations()) {
            try {
                f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                forced = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            glow = new ItemGlow(new NamespacedKey(UtilsMain.getInstance(), "Glow"));
            Enchantment.registerEnchantment(glow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (forced) {
            try {
                f = Enchantment.class.getDeclaredField("acceptingNew");
                f.set(null, false);
                f.setAccessible(false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return glow;
    }
}
