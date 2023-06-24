package com.mortisdevelopment.mortisswitchcooldown.manager;

import com.mortisdevelopment.mortiscorepaper.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SwitchCooldownCommand implements TabExecutor {

    private final SwitchCooldownManager manager;

    public SwitchCooldownCommand(SwitchCooldownManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mortisweaponcooldown.reload")) {
            sender.sendMessage(MessageUtils.color("&cYou don't have permission to use this"));
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
        manager.reload();
        sender.sendMessage(new MessageUtils("&aMortisSwitchCooldown Reloaded").color());
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
