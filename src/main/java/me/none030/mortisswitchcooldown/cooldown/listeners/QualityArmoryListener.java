package me.none030.mortisswitchcooldown.cooldown.listeners;

import me.none030.mortisswitchcooldown.MortisSwitchCooldown;
import me.none030.mortisswitchcooldown.cooldown.CooldownManager;
import me.none030.mortisswitchcooldown.cooldown.weapons.QualityArmoryWeapon;
import me.none030.mortisswitchcooldown.utils.MessageUtils;
import me.zombie_striker.qg.api.QAWeaponPrepareShootEvent;
import me.zombie_striker.qg.api.QualityArmory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class QualityArmoryListener implements Listener {

    private final MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private final CooldownManager cooldownManager;

    public QualityArmoryListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onShoot(QAWeaponPrepareShootEvent e) {
        DecimalFormat df = new DecimalFormat("#.#");
        Player player = e.getPlayer();
        if (cooldownManager.getInCoolDown().get(player.getUniqueId()) == null) {
            return;
        }
        long value = cooldownManager.getInCoolDown().get(player.getUniqueId());
        double cooldown =  (value * 5) / 20.0;
        MessageUtils utils = new MessageUtils(cooldownManager.getMessage("DENY_MESSAGE"));
        utils.replace("%cooldown%", df.format(cooldown));
        player.sendMessage(utils.getMessage());
        e.setCancelled(true);
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItem(e.getNewSlot());
        if (item == null || item.getType().isAir()) {
            return;
        }
        if (item.getItemMeta() == null) {
            return;
        }
        if (QualityArmory.getGun(item) == null) {
            return;
        }
        for (QualityArmoryWeapon weapon : cooldownManager.getQualityArmoryWeapons()) {
            if (!weapon.isWeapon(item)) {
                return;
            }
            cooldownManager.getInCoolDown().put(player.getUniqueId(), weapon.getCoolDown());
            long cooldown = weapon.getCoolDown();
            new BukkitRunnable() {
                long ticks = cooldown;
                @Override
                public void run() {
                    ticks =- 1;
                    cooldownManager.getInCoolDown().put(player.getUniqueId(), ticks);
                    if (player.getInventory().getHeldItemSlot() != e.getNewSlot()) {
                        cooldownManager.getInCoolDown().remove(player.getUniqueId());
                        cancel();
                    }
                    if (ticks <= 0) {
                        cooldownManager.getInCoolDown().remove(player.getUniqueId());
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 5L);
            return;
        }
    }
}