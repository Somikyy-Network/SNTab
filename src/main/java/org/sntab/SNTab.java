package org.sntab;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class SNTab extends JavaPlugin implements Listener {
    private  Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        config = new Configuration(this);

        TabManager tabManager = new TabManager(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(tabManager), this);

        this.getCommand("sntabreload").setExecutor(new ReloadCommand(this));

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
