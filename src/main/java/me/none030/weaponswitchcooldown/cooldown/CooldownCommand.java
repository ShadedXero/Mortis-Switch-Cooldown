package me.none030.weaponswitchcooldown.cooldown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CooldownCommand implements CommandExecutor {

    private final CooldownManager manager;

    public CooldownCommand(CooldownManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("mortisweaponcooldown.reload")) {
            return false;
        }
        if (args.length != 1) {
            return false;
        }
        if (!args[0].equalsIgnoreCase("reload")) {
            return false;
        }
        manager.reload();
        return false;
    }
}
