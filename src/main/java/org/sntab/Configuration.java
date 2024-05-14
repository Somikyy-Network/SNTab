package org.sntab;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Configuration {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public Configuration(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public String getPrefix() {
        return config.getString("prefix", "&8[&6SNTab&8] &6");
    }

    public List<String> getTabHeader() {
        return config.getStringList("Tab-Config.tab-header");
    }
    public List<String> getTabFooter() {
        return config.getStringList("Tab-Config.tab-footer");
    }

    public List<String> getSlots() {
        return config.getStringList("Tab-Config.slots");
    }

    public int getRefreshRate() {
        return config.getInt("Tab-Config.refresh-rate", 20);
    }

    public int getUpdateRate() {
        return config.getInt("Tab-Config.update-rate", 1);
    }

    public int getUpdateDelay() {
        return config.getInt("Tab-Config.update-delay", 1);
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
}