package de.mullabfuhr.magicpotions.listeners;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;


public class DropListener implements Listener {

    private static int dropEntityId;
    private static Player dropPlayer;
    private static ItemStack dropItem;

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if(event.getItemDrop().getItemStack().getType().equals(Material.valueOf(MagicPotions.getConf().getFileConfiguration().getString("portActivationItem")))) {
            dropEntityId = event.getItemDrop().getEntityId();
            dropPlayer = event.getPlayer();
            dropItem = event.getItemDrop().getItemStack();
            Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + dropPlayer.getDisplayName() + " dropped " + Material.valueOf(MagicPotions.getConf().getFileConfiguration().getString("portActivationItem")) + ". (ID: " + dropEntityId + ")");
        }

    }

    public static int getDropEntityId() {
        return dropEntityId;
    }

    public static Player getDropPlayer() {
        return dropPlayer;
    }

    public static ItemStack getDropItem() { return dropItem; }

}
