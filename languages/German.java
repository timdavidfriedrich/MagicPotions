package de.mullabfuhr.magicpotions.languages;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class German {

    private String langName;
    private FileConfiguration lang;
    private File file;
    private Boolean isNew;

    public German (String name, File path) {
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
            lang.set("command_header", List.of("Erhalte alle magischen Tränke mit \"/mp menu\".", "Und so stellst du die Tränke her:"));
            lang.set("command_footer", List.of("Mehr Infos in unter: https://www.spigotmc.org/resources/magicpotions.99138/"));
            lang.set("menu_potion_title", "Magische Tränke");
            lang.set("menu_teleport_title", "Wähle ein Ziel");
            lang.set("menu_teleport_alone_name", "Du bist allein!");
            lang.set("menu_teleport_alone_lore", List.of("Es ist niemand zum", "Teleportieren online."));
            lang.set("port_destroyed_title", "PORT ZERSTÖRT");
            lang.set("port_destroyed_subtitle", "Das ist jetzt aber blöd...");
            lang.set("port_created_title", "PORT ERSTELLT");
            lang.set("port_changed_title", "PORT GEÄNDERT");
            lang.set("potion_backport_name", "Trank des Rückrufs");
            lang.set("potion_backport_lore", List.of("Teleportiert dich", "zu deinem Port."));
            lang.set("potion_teleport_name", "Trank der Teleportation");
            lang.set("potion_teleport_lore", List.of("Teleportiert dich", "zu einem Mitspieler."));
            lang.set("potion_fly_name", "Trank der Beflügelung");
            lang.set("potion_fly_lore", List.of("Sei frei wie ein Vogel!", "Aber nur für kurze Zeit."));
            lang.set("potion_levitation_name", "Trank der Schwerelosigkeit");
            lang.set("potion_levitation_lore", List.of("Ist Gravitation auch", "ein Instrument?"));
            lang.set("fly_actionbar", "Flugzeit: ");
            lang.set("fly_warning_title", "ACHTUNG");
            lang.set("fly_warning_subtitle", "Begib dich auf eine sichere Fallhöhe!");
            lang.set("fly_actionbar_end", "Flugzeit vorbei!");
            lang.set("error_notaplayer", "Nur Spieler dürfen das tun!");
            lang.set("error_portnotfound", "Du musst zuerst einen eigenen Port erstellen! Lass dazu einfach ein Enderauge auf eine Goldene Druckplatte fallen.");
            lang.set("error_notyourport", "Dieser Port dir nicht, sondern: ");

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
