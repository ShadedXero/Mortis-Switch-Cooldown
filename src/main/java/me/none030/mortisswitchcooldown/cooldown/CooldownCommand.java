package me.none030.mortisswitchcooldown.cooldown;

import me.none030.mortisswitchcooldown.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CooldownCommand implements TabExecutor {

    private final CooldownManager cooldownManager;

    public CooldownCommand(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mortisweaponcooldown.reload")) {
            sender.sendMessage(new MessageUtils("&cYou don't have permission to use this").color());
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(new MessageUtils("&cUsage: /cooldown reload").color());
            return false;
        }
        if (!args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(new MessageUtils("&cUsage: /cooldown reload").color());
            return false;
        }
        cooldownManager.reload();
        sender.sendMessage(new MessageUtils("&aReloaded").color());
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}
