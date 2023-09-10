package de.mullabfuhr.magicpotions.listeners;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        // Wenn spieler fliegt, dann Partikel (für Trank der Beflügelung)
        if (player.isFlying() && player.getGameMode().equals(GameMode.SURVIVAL)){
            Particle.DustOptions options = new Particle.DustOptions(Color.WHITE, 1.0f);
            player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 10, options);
        }

    }

}