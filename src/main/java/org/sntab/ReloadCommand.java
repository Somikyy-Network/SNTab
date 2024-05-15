package org.sntab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final SNTab plugin;

    public ReloadCommand(SNTab plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("sntab.reload")) {
            plugin.reloadConfig();
            plugin.getPluginConfig().reloadConfig();
            TabManager tabManager = new TabManager(plugin);
            tabManager.updateTab();
            sender.sendMessage("SNTab config reloaded");
            return true;
        } else {
            sender.sendMessage("You do not have permission to use this command");
            return false;
        }
    }
}
