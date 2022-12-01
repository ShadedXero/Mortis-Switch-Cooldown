package me.none030.weaponswitchcooldown.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
