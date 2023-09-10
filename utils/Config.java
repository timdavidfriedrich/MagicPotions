package de.mullabfuhr.magicpotions.utils;

import de.mullabfuhr.magicpotions.MagicPotions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Config {

    private FileConfiguration fileConfiguration;
    private File file;
    private Boolean isNew;

    public Config (String name, File path) {
        file = new File(path, name);

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
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (isNew) {
            fileConfiguration.set("language", "en_US");
            fileConfiguration.set("portActivationItem", "ENDER_EYE");
            fileConfiguration.set("backportIngredient", "ENDER_EYE");
            fileConfiguration.set("teleportIngredient", "ENDER_PEARL");
            fileConfiguration.set("flySpeed", 0.03);
            fileConfiguration.set("flyDuration", 60);
            fileConfiguration.set("flyWarning", true);
            fileConfiguration.set("flyIngredient", "DIAMOND_BLOCK");
            fileConfiguration.set("levitationDuration", 3);
            fileConfiguration.set("levitationLevel", 6);
            fileConfiguration.set("levitationIngredient", "EMERALD");
            save();
            reload();
        }
    }

    public FileConfiguration getLangFileConfiguration() {
        String langPath = fileConfiguration.getString("language");
        File langFile = new File(MagicPotions.getPlugin().getDataFolder(), langPath + ".yml");
        File englishFile = new File(MagicPotions.getPlugin().getDataFolder(), "en_US.yml");
        FileConfiguration langFileConfiguration = new YamlConfiguration();

        if (langFile.exists()) {
            try {
                langFileConfiguration.load(langFile);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(MagicPotions.getError() + "MAGIC_POTIONS: You have to create a translation file for your selected language! Otherwise, the english language will be used instead.");
            try {
                langFileConfiguration.load(englishFile);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        return langFileConfiguration;
    }

    public String getLang(String path) {
        return getLangFileConfiguration().getString(path);
    }

    public List getLangList(String path) {
        return getLangFileConfiguration().getStringList(path);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
