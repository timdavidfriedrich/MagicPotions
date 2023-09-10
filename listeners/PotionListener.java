package de.mullabfuhr.magicpotions.listeners;

import de.mullabfuhr.magicpotions.potions.BackportPotion;
import de.mullabfuhr.magicpotions.potions.FlyPotion;
import de.mullabfuhr.magicpotions.potions.LevitationPotion;
import de.mullabfuhr.magicpotions.potions.TeleportPotion;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;


public class PotionListener implements Listener {

    @EventHandler
    public void onPotion(PlayerItemConsumeEvent event){

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // TRANK DES RÜCKRUFS
        if(item.getItemMeta().equals(BackportPotion.backportPotion.getItemMeta())) {
            BackportPotion.consumeBackportPotion(player);
        }

        // TRANK DER BEFLÜGELUNG
        if(item.getItemMeta().equals(FlyPotion.flyPotion.getItemMeta()) && player.getGameMode().equals(GameMode.SURVIVAL)) {
            FlyPotion.consumeFlyPotion(player);
        }

        // TRANK DER SCHWERELOSIGKEIT
        if(item.getItemMeta().equals(LevitationPotion.levitationPotion.getItemMeta())) {
            LevitationPotion.consumeLevitationPotion(player);
        }

        // TRANK DER TELEPORTATION
        if(item.getItemMeta().equals(TeleportPotion.teleportPotion.getItemMeta())) {
            TeleportPotion.consumeTeleportationPotion(player);
        }

    }

}