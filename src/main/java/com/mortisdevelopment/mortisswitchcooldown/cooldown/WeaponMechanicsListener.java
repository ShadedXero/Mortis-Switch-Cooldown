package com.mortisdevelopment.mortisswitchcooldown.cooldown;

import me.deecaad.weaponmechanics.weapon.weaponevents.WeaponPreShootEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;


public class WeaponMechanicsListener implements Listener {

    private final CooldownManager cooldownManager;

    public WeaponMechanicsListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onShoot(WeaponPreShootEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (!cooldownManager.getInCooldown().containsKey(player.getUniqueId())) {
            return;
        }
        cooldownManager.sendDenyMessage(player);
        e.setCancelled(true);
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack weapon = player.getInventory().getItem(e.getNewSlot());
        if (weapon == null || weapon.getType().isAir()) {
            return;
        }
        String weaponTitle = cooldownManager.getWMWeaponTitle(weapon);
        if (weaponTitle == null || !cooldownManager.getWmTitles().contains(weaponTitle)) {
            return;
        }
        long cooldown = cooldownManager.getWmCooldownByTitle().get(weaponTitle);
        cooldownManager.addCooldown(player, cooldown, e.getNewSlot());
    }
}
