package de.mullabfuhr.magicpotions;

import de.mullabfuhr.magicpotions.commands.MpCommand;
import de.mullabfuhr.magicpotions.languages.English;
import de.mullabfuhr.magicpotions.languages.German;
import de.mullabfuhr.magicpotions.listeners.*;
import de.mullabfuhr.magicpotions.potions.*;
import de.mullabfuhr.magicpotions.utils.Config;
import de.mullabfuhr.magicpotions.utils.Ports;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


    // TODO: Licht über Ports
    // TODO: "Direkt zu [NAME] oder zu dessen Port?"
    // TODO: "Willst du den Port wirklich zerstören?"
    // TODO: Port abbaubar mit Behutsamkeits-Spitzhacke
    // TODO: PlayerHeads
    // TODO: Permissions


public final class MagicPotions extends JavaPlugin {


    private static MagicPotions plugin;
    private static Config config;
    private static Ports ports;
    private static German german;
    private static English english;
    private static String language;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getPrefix() + "MagicPotions enabled.");
        plugin = this;
        config = new Config("config.yml", getDataFolder());
        ports = new Ports("ports.yml", getDataFolder());
        german = new German("de_DE.yml", getDataFolder());
        english = new English("en_US.yml", getDataFolder());
        commandRegistration();
        itemRegistration();
        listenerRegistration();
        PortListener.loadParticles();
    }

    @Override
    public void onDisable() {
        PortListener.stopParticles();
        Bukkit.getConsoleSender().sendMessage(MagicPotions.getError() + "MagicPotions disabled.");
    }


    public static MagicPotions getPlugin() {
        return plugin;
    }

    public static Ports getPorts() {
        return ports;
    }

    public static Config getConf() {
        return config;
    }


    public static String getPrefix() {
        return ChatColor.GOLD.toString() + ChatColor.BOLD + "0" + ChatColor.RESET + ChatColor.GRAY + " » " + ChatColor.GOLD;
    }

    public static String getError() {
        return ChatColor.RED.toString() + ChatColor.BOLD + "!!" + ChatColor.RESET + ChatColor.GRAY + " » " + ChatColor.RED;
    }

    public static String getQuestion() {
        return ChatColor.AQUA.toString() + ChatColor.BOLD + "?" + ChatColor.RESET + ChatColor.GRAY + " » " + ChatColor.AQUA;
    }


    private void commandRegistration(){
        getCommand("mp").setExecutor(new MpCommand());
    }

    private void itemRegistration() {
        BackportPotion.createBackportPotion();
        LevitationPotion.createLevitationPotion();
        TeleportPotion.createTeleportPotion();
        FlyPotion.createFlyPotion();
    }

    private void listenerRegistration() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BreakListener(), this);
        pm.registerEvents(new DropListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new MoveListener(), this);
        pm.registerEvents(new PortListener(), this);
        pm.registerEvents(new PotionListener(), this);
    }

}