package org.sntab;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.sntab.Expansions.PingExpansion;

public final class SNTab extends JavaPlugin {
    private  Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        config = new Configuration(this);
        TabManager tabManager = new TabManager(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tabManager), this);
        tabManager.updateTab();

        // message in a server console with color green that a plugin is started
        getLogger().info("SNTab plugin is enabled");
    }


    @Override
    public void onDisable() {
        //same console msg but with a red color and that the plugin is now off
        getLogger().info("SNTab plugin is now disabled");
        // Plugin shutdown logic
    }

    public Configuration getPluginConfig() {
        return config;
    }
}
