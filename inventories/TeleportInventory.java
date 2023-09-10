package de.mullabfuhr.magicpotions.inventories;

import de.mullabfuhr.magicpotions.MagicPotions;
import net.minecraft.world.item.ItemSkullPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeleportInventory implements InventoryHolder {

    private Inventory teleport;

    public TeleportInventory() {
        teleport = Bukkit.createInventory(this, getSize(), MagicPotions.getConf().getLang("menu_teleport_title"));
        init();
    }

    private void init() {
        ItemStack item;
        if (Bukkit.getServer().getOnlinePlayers().size() == 1) {
            item = emptyItem();
            teleport.addItem(item);
        } else {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                item = createItem(player);
                teleport.addItem(item);
            }
        }
    }

    private ItemStack createItem(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(player.getDisplayName());
        Location loc = player.getLocation();
        meta.setLore(List.of(ChatColor.RESET.toString() + ChatColor.GRAY + loc.getBlockX() + " / " + loc.getBlockY() + " / " + loc.getBlockZ() + ""));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack emptyItem() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        List<String> loreRaw = MagicPotions.getConf().getLangList("menu_teleport_alone_lore");
        meta.setDisplayName(ChatColor.RESET.toString() + ChatColor.RED + ChatColor.BOLD + MagicPotions.getConf().getLang("menu_teleport_alone_name"));
        for (String line: loreRaw) {
            lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + line);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private int getSize() {
        int size = Bukkit.getServer().getOnlinePlayers().size() - 1;
        if (size <= 9) {
            return 9;
        } else if (size <= 18) {
            return 18;
        } else if (size <= 27) {
            return 27;
        } else if (size <= 36) {
            return 36;
        } else if (size <= 45) {
            return 45;
        } else if (size <= 54) {
            return 54;
        } else {
            Bukkit.getConsoleSender().sendMessage(MagicPotions.getError() + "Too many players are online to show them all in the teleport menu.");
            return 54;
        }
    }

    @Override
    public Inventory getInventory() {
        return teleport;
    }

}