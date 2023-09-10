package de.mullabfuhr.magicpotions.potions;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;


public class BackportPotion {

    public static ItemStack backportPotion;

    public static void createBackportPotion() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        List<String> lore = new ArrayList<>();
        List<String> loreRaw = MagicPotions.getConf().getLangList("potion_backport_lore");
        for (String line: loreRaw) {
            lore.add(ChatColor.RESET.toString() + ChatColor.BLUE + line);
        }
        meta.setLore(lore);
        meta.setColor(Color.YELLOW);
        meta.setDisplayName(ChatColor.RESET + MagicPotions.getConf().getLang("potion_backport_name"));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        backportPotion = item;

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("backport_item"), item);
        recipe.addIngredient(1, Material.getMaterial(MagicPotions.getConf().getFileConfiguration().getString("backportIngredient")));
        recipe.addIngredient(1, Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);
    }

    public static void consumeBackportPotion(Player player) {
        Location port = MagicPotions.getPorts().getFileConfiguration().getLocation(player.getUniqueId().toString());
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + player.getDisplayName() + " consumed a potion of backporting!");
        if (port != null) {
            player.teleport(port);
            TeleportPotion.teleportEffect(player);
        } else {
            player.sendMessage(MagicPotions.getError() + MagicPotions.getConf().getLang("error_portnotfound"));
        }
    }

}