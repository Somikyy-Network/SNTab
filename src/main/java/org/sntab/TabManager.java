package org.sntab;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

        //print the header and footer values for devugging
        System.out.println("Header: " + header);
        System.out.println("Footer: " + footer);

        // check if the header and footer are empty
        if (header == null || footer == null) {
            plugin.getLogger().warning("Tab header or footer is empty");
            return;
        }

        // Convert the header and footer to a single string
        String headerString = String.join("\n", header);
        String footerString = String.join("\n", footer);

        // print the colorized header and footer values for debugging
        System.out.println("Colorized Header: " + colorize(header));
        System.out.println("Colorized Footer: " + colorize(footer));

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

        // Update the tab list for each online player
        for (Player player : Bukkit.getOnlinePlayers()) {
            String parsedHeader = parsePlaceholders(player, headerString);
            String parsedFooter = parsePlaceholders(player, footerString);

            player.setPlayerListHeaderFooter(parsedHeader, parsedFooter);
        }

        // Schedule the next update
        plugin.getServer().getScheduler().runTaskLater(plugin, this::updateTab, refreshRate);
    }

    private List<String> colorize(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace("&", "§"))
                .collect(Collectors.toList());
    }

    private String parsePlaceholders(Player player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        } else {
            return text;
        }
    }
}
