package org.sntab;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TabManager {
    private final SNTab plugin;
    private final Configuration config;

    public TabManager(SNTab plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
    }

    public void updateTab() {
        // Update the tab list

        // Get the tab header and footer
        List<String> header = config.getTabHeader();
        List<String> footer = config.getTabFooter();

        // check if the header and footer are empty
        if (header == null || footer == null) {
            plugin.getLogger().warning("Tab header or footer is empty");
            return;
        }

        // Convert the header and footer to a single string
        String headerString = String.join("\n", header);
        String footerString = String.join("\n", footer);

        // Update the tab list for each online player
        for (Player player : Bukkit.getOnlinePlayers()) {
            String parsedHeader = parsePlaceholders(player, headerString);
            String parsedFooter = parsePlaceholders(player, footerString);

            // Colorize after parsing placeholders
            List<String> colorizedHeader = colorize(Arrays.asList(parsedHeader.split("\n")));
            List<String> colorizedFooter = colorize(Arrays.asList(parsedFooter.split("\n")));

            // Convert back to single string
            String colorizedHeaderString = String.join("\n", colorizedHeader);
            String colorizedFooterString = String.join("\n", colorizedFooter);

            player.setPlayerListHeaderFooter(colorizedHeaderString, colorizedFooterString);

            List<String> slots = config.getSlots();

            // Parse the placeholders in each slot and set the parsed text as the player's tab list name
            for (String slot : slots) {
                String parsedSlot = parsePlaceholders(player, slot);
                player.setPlayerListName(parsedSlot);
            }
        }

        // Get the slots
        List<String> slots = config.getSlots();

        // Get the refresh rate
        int refreshRate = config.getRefreshRate();

        // check if the refresh rate is a positive number
        if (refreshRate <= 0) {
            plugin.getLogger().warning("Refresh rate must be a positive number");
            return;
        }

        // Get the update rate
        int updateRate = config.getUpdateRate();

        // Get the update delay
        int updateDelay = config.getUpdateDelay();

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

        for (Player player : onlinePlayers) {
            updatePlayerListName(player);
        }

        // Schedule the next update
        plugin.getServer().getScheduler().runTaskLater(plugin, this::updateTab, refreshRate);

        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerListName(player);
        }
    }

    private void updatePlayerListName(Player player) {
        String prefix = parsePlaceholders(player, "%vault_prefix%");
        String suffix = parsePlaceholders(player, "%vault_suffix%");
        prefix = prefix.replace("&", "§");
        suffix = suffix.replace("&", "§");
        int ping = player.getPing();
        String pingColor = getPingColor(ping);

        player.setPlayerListName(prefix + player.getName() + " " + suffix + " " + pingColor + ping + "ms");
    }

    private List<String> colorize(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace("&", "§"))
                .collect(Collectors.toList());
    }

    private String parsePlaceholders(Player player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            String parsedText = PlaceholderAPI.setPlaceholders(player, text);
            return parsedText;
        } else {
            plugin.getLogger().warning("------------------------------------------------------------------");
            plugin.getLogger().warning("PlaceholderAPI is not enabled/installed");
            plugin.getLogger().warning("You should install it so SNTab works. (maybe install expansions?)");
            plugin.getLogger().warning("------------------------------------------------------------------");
            return text;
        }
    }

    private String getPingColor(int ping) {
        if (ping < 40) {
            return "§a"; // Green
        } else if (ping < 90) {
            return "§a"; // Lime
        } else if (ping < 130) {
            return "§e"; // Yellow
        } else if (ping < 175) {
            return "§6"; // Orange
        } else if (ping < 200) {
            return "§c"; // Red
        } else {
            return "§0"; // Black
        }
    }
}