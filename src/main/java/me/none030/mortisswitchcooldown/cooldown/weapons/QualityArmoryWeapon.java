package me.none030.mortisswitchcooldown.cooldown.weapons;

import me.zombie_striker.qg.api.QualityArmory;
import me.zombie_striker.qg.guns.Gun;
import org.bukkit.inventory.ItemStack;

public class QualityArmoryWeapon extends Weapon {

    public QualityArmoryWeapon(String weaponId, long coolDown) {
        super(weaponId, coolDown);
    }

    @Override
    public boolean isWeapon(ItemStack weapon) {
        Gun gun = QualityArmory.getGun(weapon);
        if (gun == null) {
            return false;
        }
        return isId(gun.getName());
    }
}
