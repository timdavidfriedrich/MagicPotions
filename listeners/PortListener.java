package de.mullabfuhr.magicpotions.listeners;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PortListener implements Listener {

    private static Location location;
    public static BukkitRunnable particles;
    public static final Particle.DustOptions particleOptionsTop = new Particle.DustOptions(Color.YELLOW, 0.4f);
    public static final Particle.DustOptions particleOptionsBottom = new Particle.DustOptions(Color.ORANGE, 0.8f);
    public static final Particle.DustOptions particleOptionsSmoke = new Particle.DustOptions(Color.BLACK, 0.2f);

    @EventHandler
    public void onPressurePlate(EntityInteractEvent event) {
        Block block = event.getBlock();
        Entity entity = event.getEntity();
        // Wenn Enderauge auf goldene Druckplatte, dann Origin für Spieler:in gesetzt
        if (DropListener.getDropItem().getType().equals(Material.valueOf(MagicPotions.getConf().getFileConfiguration().getString("portActivationItem"))) && block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE) && entity.getEntityId() == DropListener.getDropEntityId()) {
            Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + MagicPotions.getConf().getFileConfiguration().getString("portActivationItem") + " dropped on a golden pressure plate! ");
            FileConfiguration ports = MagicPotions.getPorts().getFileConfiguration();
            Player player = DropListener.getDropPlayer();
            if (!ports.getKeys(false).contains(player.getUniqueId().toString())) {
                createPort(block, entity);
                player.sendTitle(ChatColor.GOLD + MagicPotions.getConf().getLang("port_created_title"), ChatColor.WHITE + "(" + block.getX() + "/" + block.getY() + "/" + block.getZ() + ")", 20, 30, 30);
            } else {
                removePort(player);
                createPort(block, entity);
                player.sendTitle(ChatColor.GOLD + MagicPotions.getConf().getLang("port_changed_title"), ChatColor.WHITE + "(" + block.getX() + "/" + block.getY() + "/" + block.getZ() + ")", 20, 30, 30);
            }
        }
    }

    private void createPort(Block block, Entity entity) {
        location = new Location(block.getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5);

        Player player = DropListener.getDropPlayer();
        location.setYaw(player.getLocation().getYaw() - 180); // Blickrichtung wird umgedreht
        MagicPotions.getPorts().getFileConfiguration().set(player.getUniqueId().toString(), location);
        MagicPotions.getPorts().save();
        MagicPotions.getPorts().reload();

        reloadParticles();
        block.getWorld().playSound(location, Sound.BLOCK_END_PORTAL_SPAWN, 0.5f, 1);
        block.getWorld().spawnParticle(Particle.LAVA, location, 32);
        block.getWorld().spawnParticle(Particle.FLASH, location, 2);
        block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, location, 200);
        entity.remove();
    }

    public static void removePort(Player player) {
        MagicPotions.getPorts().getFileConfiguration().set(player.getUniqueId().toString(), null);
        MagicPotions.getPorts().save();
        MagicPotions.getPorts().reload();
        reloadParticles();
    }

    public static void loadParticles() {
        FileConfiguration ports = MagicPotions.getPorts().getFileConfiguration();
        for (String path: ports.getKeys(false)) {
            /*
            Bukkit.getConsoleSender().sendMessage(ports.getLocation(path).getBlock().toString());
            Bukkit.getConsoleSender().sendMessage(ports.getLocation(path).add(0, 1, 0).getBlock().toString());
            Block portTop = ports.getLocation(path).getBlock();
            if (portTop.getType().equals(Material.AIR)) {
                portTop.setType(Material.LIGHT);
                Levelled portLight = (Levelled) portTop;
                portLight.setLevel(6);
            }
            */
            particles = new BukkitRunnable() {
                @Override
                public void run(){
                    ports.getLocation(path).getWorld().spawnParticle(Particle.REDSTONE, ports.getLocation(path), 15, 0.20, 0.75, 0.20, particleOptionsTop);
                    ports.getLocation(path).getWorld().spawnParticle(Particle.REDSTONE, ports.getLocation(path), 5, 0.20, 0.40, 0.20, particleOptionsBottom);
                    ports.getLocation(path).getWorld().spawnParticle(Particle.REDSTONE, ports.getLocation(path), 5, 0.30, 0.80, 0.30, particleOptionsSmoke);
                }};
            particles.runTaskTimer(MagicPotions.getPlugin(), 0, 1);
        }
    }

    public static void stopParticles() {
        /*
        FileConfiguration ports = MagicPotions.getPorts().getFileConfiguration();
        for (String path: ports.getKeys(false)) {
            Block portTop = ports.getLocation(path).add(0, 0.5, 0).getBlock();
            if (portTop.getType().equals(Material.LIGHT)) {
                portTop.setType(Material.AIR);
            }
        }
        */
        if (particles != null) {
            particles.cancel();
        }
    }

    public static void reloadParticles() {
        stopParticles();
        loadParticles();
    }

}

/*
for (Entity p : entity.getNearbyEntities(6, 6, 6)) {
    if(p instanceof Player player) {
        player.sendTitle(ChatColor.GOLD + "PORT-BLOCK", ChatColor.WHITE + "(" + block.getX() + "/" + block.getY() + "/" + block.getZ() + ")", 20, 30, 30);
    }
}
*/