package de.mullabfuhr.magicpotions.languages;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class English {

    private String langName;
    private FileConfiguration lang;
    private File file;
    private Boolean isNew;

    public English (String name, File path) {
        file = new File(path, name);
        langName = name;

        if (file.exists()) {
            isNew = false;
        } else {
            path.mkdirs();
            try {
                file.createNewFile();
                isNew = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lang = new YamlConfiguration();
        try {
            lang.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (isNew) {
            lang.set("command_header", List.of("Get a menu of all potions with '/mp menu'.", "And here is how you craft the magic potions:"));
            lang.set("command_footer", List.of("More details: https://www.spigotmc.org/resources/magicpotions.99138/"));
            lang.set("menu_potion_title", "Magic Potions");
            lang.set("menu_teleport_title", "Choose a destination");
            lang.set("menu_teleport_alone_name", "You are alone!");
            lang.set("menu_teleport_alone_lore", List.of("There is nobody", "to teleport to."));
            lang.set("port_destroyed_title", "PORT DESTROYED");
            lang.set("port_destroyed_subtitle", "That's too bad...");
            lang.set("port_created_title", "PORT CREATED");
            lang.set("port_changed_title", "PORT CHANGED");
            lang.set("potion_backport_name", "Potion of Backporting");
            lang.set("potion_backport_lore", List.of("Teleports you", "to your port."));
            lang.set("potion_teleport_name", "Potion of Teleportation");
            lang.set("potion_teleport_lore", List.of("Teleports you", "to a player."));
            lang.set("potion_fly_name", "Potion of Freedom");
            lang.set("potion_fly_lore", List.of("Fly like a bird!", "But only for a short time."));
            lang.set("potion_levitation_name", "Potion of Levitation");
            lang.set("potion_levitation_lore", List.of("What is gravitation?", "Can you eat that?"));
            lang.set("fly_actionbar", "Flight time: ");
            lang.set("fly_warning_title", "WARNING");
            lang.set("fly_warning_subtitle", "Get to a safe fall height!");
            lang.set("fly_actionbar_end", "Flight time over!");
            lang.set("error_notaplayer", "Only players can do that!");
            lang.set("error_portnotfound", "At first, you have to create your own port! Just drop an eye of ender on a golden pressure plate.");
            lang.set("error_notyourport", "That port belongs to: ");
            save();
            reload();
        }
    }

    public String getLangName() {
        return langName;
    }

    public String getString(String path) {
        return lang.getString(path);
    }

    public List getList(String path) {
        return lang.getStringList(path);
    }

    public void save() {
        try {
            lang.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            lang.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
