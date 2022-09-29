package me.none030.weaponswitchcooldown.events;

import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.none030.weaponswitchcooldown.WeaponSwitchCooldown.plugin;

public class Event implements Listener {

    HashMap<UUID, Double> inCooldown = new HashMap<>();

    @EventHandler
    public void onShoot(WeaponPrepareShootEvent e) {

        Player player = e.getPlayer();

        if (inCooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(plugin.getConfig().getString("plugin.deny-message").replace("%cooldown%", inCooldown.get(player.getUniqueId()).toString()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {

        FileConfiguration config = plugin.getConfig();
        List<String> weapons = config.getStringList("plugin.weapons");
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItem(e.getNewSlot());
        if (item == null || item.getType().equals(Material.AIR)) {
            return;
        }
        if (item.getItemMeta() == null) {
            return;
        }
        if (!item.getItemMeta().hasDisplayName()) {
            return;
        }

        for (String weapon : weapons) {

            String[] raw = weapon.split(":");

            if (item.getItemMeta().getDisplayName().contains(raw[0])) {
                final double[] timer = {Double.parseDouble(raw[1])};
                inCooldown.put(player.getUniqueId(), Double.parseDouble(raw[1]));

                if (timer[0] >= 1.0) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            timer[0] = timer[0] - 1.0;
                            inCooldown.put(player.getUniqueId(), timer[0]);
                            if (player.getInventory().getHeldItemSlot() != e.getNewSlot()) {
                                inCooldown.remove(player.getUniqueId());
                                cancel();
                            }
                            if (timer[0] <= 0.0) {
                                inCooldown.remove(player.getUniqueId());
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 20L);
                }
                else {
                    inCooldown.put(player.getUniqueId(), timer[0]);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            inCooldown.remove(player.getUniqueId());
                            cancel();
                        }
                    }.runTaskLater(plugin, (long) (timer[0] * 20L));
                }
            }
        }
    }
}
