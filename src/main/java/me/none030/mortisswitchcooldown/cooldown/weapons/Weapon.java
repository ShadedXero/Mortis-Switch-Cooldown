package me.none030.mortisswitchcooldown.cooldown.weapons;

import org.bukkit.inventory.ItemStack;

public abstract class Weapon {

    private final String weaponId;
    private final long coolDown;

    public Weapon(String weaponId, long coolDown) {
        this.weaponId = weaponId;
        this.coolDown = coolDown;
    }

    public boolean isId(String weaponId) {
        return this.weaponId.equals(weaponId);
    }

    public abstract boolean isWeapon(ItemStack weapon);

    public String getWeaponId() {
        return weaponId;
    }

    public long getCoolDown() {
        return coolDown;
    }
}
