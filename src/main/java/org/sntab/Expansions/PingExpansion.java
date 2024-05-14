package org.sntab.Expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PingExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "ping";
    }

    @Override
    public String getAuthor() {
        return "somikyy";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        if ("player_ping".equals(identifier)) {
            return String.valueOf(player.getPing());
        }

        return null;
    }

    @Override
    public boolean register() {
        boolean registered = super.register();
        if (registered) {
            Bukkit.getLogger().info("PingExpansion has been registered successfully.");
        } else {
            Bukkit.getLogger().info("PingExpansion could not be registered.");
        }
        return registered;
    }
}
