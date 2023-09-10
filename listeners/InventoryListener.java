package de.mullabfuhr.magicpotions.listeners;

import de.mullabfuhr.magicpotions.MagicPotions;
import de.mullabfuhr.magicpotions.inventories.TeleportInventory;
import de.mullabfuhr.magicpotions.potions.TeleportPotion;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) { return; }

        if (event.getClickedInventory().getHolder() instanceof TeleportInventory) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) { return; }

            Player player = (Player) event.getWhoClicked();
            Player destination = Bukkit.getServer().getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());

            if (!event.getCurrentItem().getType().equals(Material.BARRIER)) {
                if (!player.equals(destination)) {
                    player.teleport(destination.getLocation());
                    TeleportPotion.teleportEffect(player);
                } else {
                    player.sendMessage(MagicPotions.getError() + "Unexpected error! (InventoryListener)");
                }
            }

        }

    }

}