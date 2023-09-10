package de.mullabfuhr.magicpotions.inventories;

import de.mullabfuhr.magicpotions.MagicPotions;
import de.mullabfuhr.magicpotions.potions.BackportPotion;
import de.mullabfuhr.magicpotions.potions.FlyPotion;
import de.mullabfuhr.magicpotions.potions.LevitationPotion;
import de.mullabfuhr.magicpotions.potions.TeleportPotion;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PluginInventory implements InventoryHolder {


    private Inventory pluginInventory;

    public PluginInventory() {
        pluginInventory = Bukkit.createInventory(this, 9, MagicPotions.getConf().getLang("menu_potion_title"));
        pluginInventory.addItem(BackportPotion.backportPotion);
        pluginInventory.addItem(TeleportPotion.teleportPotion);
        pluginInventory.addItem(FlyPotion.flyPotion);
        pluginInventory.addItem(LevitationPotion.levitationPotion);
    }

    @Override
    public Inventory getInventory() {
        return pluginInventory;
    }

}