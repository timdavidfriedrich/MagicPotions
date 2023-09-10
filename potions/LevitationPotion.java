package de.mullabfuhr.magicpotions.potions;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;


public class LevitationPotion {

    public static ItemStack levitationPotion;
    private static final int levitationDuration = MagicPotions.getConf().getFileConfiguration().getInt("levitationDuration");
    private static final int levitationLevel = MagicPotions.getConf().getFileConfiguration().getInt("levitationLevel")  - 1;

    public static void createLevitationPotion() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        List<String> lore = new ArrayList<>();
        List<String> loreRaw = MagicPotions.getConf().getLangList("potion_levitation_lore");
        for (String line: loreRaw) {
            lore.add(ChatColor.RESET.toString() + ChatColor.BLUE + line);
        }
        meta.setLore(lore);
        meta.setColor(Color.GREEN);
        meta.setDisplayName(ChatColor.RESET + MagicPotions.getConf().getLang("potion_levitation_name"));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        levitationPotion = item;

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("levitation_item"), item);
        recipe.addIngredient(1, Material.getMaterial(MagicPotions.getConf().getFileConfiguration().getString("levitationIngredient")));
        recipe.addIngredient(1, Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);

    }

    public static void consumeLevitationPotion(Player player) {
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + player.getDisplayName() + " consumed a potion of levitation!");
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PARROT_IMITATE_ENDER_DRAGON, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, levitationDuration * 20, levitationLevel, true, true));
    }

}