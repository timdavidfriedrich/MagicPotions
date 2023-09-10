package de.mullabfuhr.magicpotions.potions;

import de.mullabfuhr.magicpotions.MagicPotions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class FlyPotion {

    public static ItemStack flyPotion;

    private static int flyTimer;
    private static final int flyDuration = MagicPotions.getConf().getFileConfiguration().getInt("flyDuration");
    private static final int flyTicks = flyDuration * 20;
    private static final float flySpeed = (float) MagicPotions.getConf().getFileConfiguration().getDouble("flySpeed");
    private static BukkitRunnable flyRunnable;
    private static BukkitRunnable flyAfter;


    public static void createFlyPotion() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        List<String> lore = new ArrayList<>();
        List<String> loreRaw = MagicPotions.getConf().getLangList("potion_fly_lore");
        for (String line: loreRaw) {
            lore.add(ChatColor.RESET.toString() + ChatColor.BLUE + line);
        }
        meta.setLore(lore);
        meta.setColor(Color.AQUA);
        meta.setDisplayName(ChatColor.RESET + MagicPotions.getConf().getLang("potion_fly_name"));
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        flyPotion = item;

        ShapelessRecipe recipe = new ShapelessRecipe(NamespacedKey.minecraft("fly_item"), item);
        recipe.addIngredient(1, Material.getMaterial(MagicPotions.getConf().getFileConfiguration().getString("flyIngredient")));
        recipe.addIngredient(1, Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);

    }

    public static void consumeFlyPotion(Player player) {
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + player.getDisplayName() + " consumed a potion of freedom!");
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        player.setAllowFlight(true);
        player.setFlySpeed(flySpeed);

        flyTimer = flyDuration;
        flyRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (flyTimer > 20) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + MagicPotions.getConf().getLang("fly_actionbar") + ChatColor.WHITE + ChatColor.BOLD + flyTimer + "s"));
                } else if (flyTimer > 10) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + MagicPotions.getConf().getLang("fly_actionbar") + ChatColor.WHITE + ChatColor.BOLD + flyTimer + "s"));
                } else {
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + MagicPotions.getConf().getLang("fly_actionbar") + ChatColor.WHITE + ChatColor.BOLD + flyTimer + "s"));
                }
                if (flyTimer < 10 && flyTimer > 6 && MagicPotions.getConf().getFileConfiguration().getBoolean("flyWarning")) {
                    player.sendTitle(MagicPotions.getConf().getLang("fly_warning_title"), ChatColor.RED + MagicPotions.getConf().getLang("fly_warning_subtitle"), 0, 30, 0);
                }
                flyTimer--;
            }};
        flyAfter = new BukkitRunnable() {
            @Override
            public void run() {
                player.setAllowFlight(false);
                player.setFlySpeed(0.2f);
                flyRunnable.cancel();
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + ChatColor.BOLD + MagicPotions.getConf().getLang("fly_actionbar_end")));
                Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + player.getDisplayName() + "'s flight time is over!");
            }};
        flyRunnable.runTaskTimer(MagicPotions.getPlugin(), 0, 20);
        flyAfter.runTaskLater(MagicPotions.getPlugin(), flyTicks);
    }

}