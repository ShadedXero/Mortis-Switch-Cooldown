package me.none030.weaponswitchcooldown.cooldown;

import com.shampaggon.crackshot.CSUtility;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import me.none030.weaponswitchcooldown.WeaponSwitchCooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

import static me.none030.weaponswitchcooldown.cooldown.CooldownConfigManager.DenyMessage;

public class CooldownListener implements Listener {

    public WeaponSwitchCooldown plugin = WeaponSwitchCooldown.getInstance();
    public CSUtility crackShot = WeaponSwitchCooldown.getCrackShot();

    public CooldownManager manager;

    public CooldownListener(CooldownManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onShoot(WeaponPrepareShootEvent e) {

        DecimalFormat df = new DecimalFormat("#.#");
        Player player = e.getPlayer();

        if (manager.getInCooldown().get(player.getUniqueId()) == null) {
            return;
        }
        long value = manager.getInCooldown().get(player.getUniqueId());
        double cooldown =  value / 20.0;
        player.sendMessage(DenyMessage.replace("%cooldown%", df.format(cooldown)));
        e.setCancelled(true);
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {

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

        String title = crackShot.getWeaponTitle(item);
        for (Weapon weapon : manager.getCooldownConfigManager().getWeapons()) {
            if (title.equals(weapon.getName())) {
                manager.getInCooldown().put(player.getUniqueId(), weapon.getCooldown());
                long cooldown = weapon.getCooldown();
                long[] count = {cooldown};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        count[0] = count[0] - 1;
                        manager.getInCooldown().put(player.getUniqueId(), count[0]);
                        if (player.getInventory().getHeldItemSlot() != e.getNewSlot()) {
                            manager.getInCooldown().remove(player.getUniqueId());
                            cancel();
                        }
                        if (count[0] <= 0) {
                            manager.getInCooldown().remove(player.getUniqueId());
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 5L);
                break;
            }
        }
    }
}
