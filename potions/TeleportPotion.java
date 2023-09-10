package de.mullabfuhr.magicpotions.potions;

import de.mullabfuhr.magicpotions.MagicPotions;
import de.mullabfuhr.magicpotions.inventories.TeleportInventory;
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


public class TeleportPotion {

    public static ItemStack teleportPotion;

    public static void createTeleportPotion() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        List<String> lore = new ArrayList<>();
        List<String> loreRaw = MagicPotions.getConf().getLangList("potion_teleport_lore");
        for (String line: loreRaw) {
            lore.add(ChatColor.RESET.toString() + ChatColor.BLUE + line);
        }
        meta.setLore(lore);
        meta.setColor(Color.FUCHSIA);
        meta.setDisplayName(ChatColor.RESET + MagicPotions.getConf().getLang("potion_teleport_name"));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        teleportPotion = item;

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("teleport_item"), item);
        recipe.addIngredient(1, Material.getMaterial(MagicPotions.getConf().getFileConfiguration().getString("teleportIngredient")));
        recipe.addIngredient(1, Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);

    }

    public static void consumeTeleportationPotion(Player player) {
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + player.getDisplayName() + " consumed a potion of teleportation!");
        player.openInventory(new TeleportInventory().getInventory());
    }

    public static void teleportEffect(Player player) {
        player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation(), 20);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 5, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 25, 4, false, false));
    }

}