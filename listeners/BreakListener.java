package de.mullabfuhr.magicpotions.listeners;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class BreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        FileConfiguration ports = MagicPotions.getPorts().getFileConfiguration();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = new Location(block.getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5);
        Location port = ports.getLocation(player.getUniqueId().toString());
        port.setYaw(0.0f);

        if (block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
            if (ports.getKeys(false).contains(player.getUniqueId().toString())) {
                if (location.equals(port)) {
                    PortListener.removePort(player);
                    PortListener.reloadParticles();
                    block.getWorld().playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.5f, 1);
                    block.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location, 100);
                    player.sendTitle(ChatColor.GOLD + MagicPotions.getConf().getLang("port_destroyed_title"), ChatColor.WHITE + MagicPotions.getConf().getLang("port_destroyed_subtitle"), 20, 30, 30);
                } else {
                    for (String path: ports.getKeys(false)) {
                        Location tempPort = ports.getLocation(path);
                        tempPort.setYaw(0.0f);
                        if (location.equals(tempPort)) {
                            Player tempPlayer = Bukkit.getPlayer(path);
                            player.sendMessage(MagicPotions.getError() + MagicPotions.getConf().getLang("error_notyourport") + tempPlayer.getDisplayName());
                            new BukkitRunnable() {
                                @Override
                                public void run(){
                                    block.setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                                }}.runTaskLater(MagicPotions.getPlugin(), 30);
                        }
                    }
                }

            }
        }
    }

}
