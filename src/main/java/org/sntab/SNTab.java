package org.sntab;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

public final class SNTab extends JavaPlugin {
    private  Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = new Configuration(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Configuration getPluginConfig() {
        return config;
    }
}
