package de.mullabfuhr.magicpotions.commands;

import de.mullabfuhr.magicpotions.MagicPotions;
import de.mullabfuhr.magicpotions.inventories.PluginInventory;
import de.mullabfuhr.magicpotions.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.List;


public class MpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Config config = MagicPotions.getConf();
        FileConfiguration fileConfig = MagicPotions.getConf().getFileConfiguration();

        if (!(sender instanceof Player player)) {
            sender.sendMessage(MagicPotions.getError() + config.getLang("error_notaplayer"));
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("menu")) {
            player.openInventory(new PluginInventory().getInventory());
        } else {
            List<String> header_lines = config.getLangList("command_header");
            List<String> footer_lines = config.getLangList("command_footer");

            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "MagicPotions-Plugin:");
            for (String line: header_lines) {
                player.sendMessage(MagicPotions.getPrefix() + line);
            }
            player.sendMessage(MagicPotions.getPrefix() + config.getLang("potion_backport_name") + ": " + ChatColor.RESET + ChatColor.GRAY + fileConfig.getString("backportIngredient") + " + " + "GLASS_BOTTLE");
            player.sendMessage(MagicPotions.getPrefix() + config.getLang("potion_teleport_name") + ": " + ChatColor.RESET + ChatColor.GRAY + fileConfig.getString("teleportIngredient") + " + " + "GLASS_BOTTLE");
            player.sendMessage(MagicPotions.getPrefix() + config.getLang("potion_fly_name") + ": " + ChatColor.RESET + ChatColor.GRAY + fileConfig.getString("flyIngredient") + " + " + "GLASS_BOTTLE");
            player.sendMessage(MagicPotions.getPrefix() + config.getLang("potion_levitation_name") + ": " + ChatColor.RESET + ChatColor.GRAY + fileConfig.getString("levitationIngredient") + " + " + "GLASS_BOTTLE");
            for (String line: footer_lines) {
                player.sendMessage(MagicPotions.getPrefix() + line);
            }
        }

        return false;
    }
}